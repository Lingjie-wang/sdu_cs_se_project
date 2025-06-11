import {PlusOutlined} from '@ant-design/icons';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {Button, Image, Input, message, Modal, Radio} from 'antd';
import React, {useRef, useState} from 'react';
import {addUser, deleteUser, getUserList, updateUser} from "@/services/hr-pro/api";
import CreateModal from "@/pages/admin/UserManage/components/CreateModal";
import UpdateModal from "@/pages/admin/UserManage/components/UpdateModal";

const UserManage: React.FC = () => {
  // useState 当前的 state(createModalVisible) 用于更新它的函数(handleCreateModalVisible())
  // 第一次显示时 createModalVisible 为 false
  // 当想改变 state(createModalVisible) 的值时 调用 handleModalVisible() 将新值传给它
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, handleCurrentRow] = useState<API.CurrentUser>();

  // 创建用户表单提交
  const handleAddUser = async (fields: API.UserAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addUser({
        ...fields,
      });
      hide();
      message.success('创建成功');
      handleCreateModalVisible(false);
      actionRef.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('创建失败，' + error.message);
      return false;
    }
  };

  const handleDeleteUser = async (record: API.UserDeleteRequest) => {
    Modal.confirm({
      title: '你确定删除当前选中用户吗？',
      content: '删除这条用户信息后，系统用户数据不可恢复',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在删除');
        if (!record) return true;
        try {
          await deleteUser({
            ...record,
          });
          hide();
          message.success('删除成功，正在刷新');
          actionRef.current?.reload();
          return true;
        } catch (error: any) {
          hide();
          message.error('删除失败，请重试');
          return false;
        }
      },
    });
  };

  const handleUpdateUser = async (fields: API.UserUpdateRequest) => {
    const hide = message.loading('正在更新');
    try {
      await updateUser({
        // 显式将 id 属性传递给后端
        id: currentRow?.id,
        ...fields,
      });
      hide();
      message.success('更新成功');
      handleUpdateModalVisible(false);
      actionRef.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('更新失败，' + error.message);
      return false;
    }
  };

  const columns: ProColumns<API.CurrentUser>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      align: 'center',
      search: false,
      //valueType: 'indexBorder',
    },
    {
      title: '用户名',
      dataIndex: 'userName',
      align: 'center',
      search: false,
    },
    {
      title: '用户账号',
      dataIndex: 'userAccount',
      align: 'center',
      copyable: true,
    },
    {
      title: '用户头像',
      dataIndex: 'userAvatar',
      align: 'center',
      search: false,
      render: (_, record) => (
        <div>
          <Image src={record.userAvatar} width={50} height={50}
                 fallback="https://img.ixintu.com/download/jpg/20200910/f9256155491e54bf5e99bf29eece0156_512_512.jpg!ys"
          />
        </div>
      ),
    },
    {
      title: '用户角色',
      dataIndex: 'userRole',
      valueType: 'select',
      align: 'center',
      valueEnum: {
        0: {
          text: '普通用户',
          status: 'Default'
        },
        1: {
          text: '管理员',
          status: 'Success'
        },
      },
    },
    {
      title: '注册时间',
      dataIndex: 'registerTime',
      valueType: 'dateTime',
      align: 'center',
      search: false,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      render: (_, record) => [
        <Button
          type="primary"
          shape="round"
          key="config"
          onClick={() => {
            handleUpdateModalVisible(true);
            handleCurrentRow(record);
          }}
        >
          更新
        </Button>,

        <Button
          type="dashed"
          shape="round"
          key="config"
          danger
          onClick={() => {
            handleDeleteUser(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];

  const addUserColumns: ProColumns<API.UserAddRequest>[] = [
    {
      title: '用户名',
      dataIndex: 'userName',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入用户名',
          },
        ],
      },
    },
    {
      title: '用户账号',
      dataIndex: 'userAccount',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入账号',
          },
          {
            min: 4,
            message: '账号不能小于4位',
          },
        ],
      },
    },
    {
      title: '密码',
      dataIndex: 'userPassword',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入密码',
          },
          {
            min: 8,
            message: '密码长度不能小于8位',
          },
        ],
      },
      renderFormItem: () => <Input.Password/>,
    },
    {
      title: '用户头像',
      dataIndex: 'userAvatar',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入用户头像网址',
          },
        ],
      },
    },
    {
      title: '用户角色',
      dataIndex: 'userRole',
      valueType: 'select',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择用户角色',
          },
        ],
      },
      valueEnum: {
        0: {
          text: '普通用户',
          status: 'Default'
        },
        1: {
          text: '管理员',
          status: 'Success'
        },
      },
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      renderFormItem: (item, {value, onChange}, form) => (
        <Radio.Group value={form.getFieldValue(item.dataIndex)}>
          <Radio value={0}>普通用户</Radio>
          <Radio value={1}>管理员</Radio>
        </Radio.Group>
      ),
    },
  ];

  const updateUserColumns: ProColumns<API.UserAddRequest>[] = [
    {
      title: '用户名',
      dataIndex: 'userName',
    },
    {
      title: '用户账号',
      dataIndex: 'userAccount',
    },
    {
      title: '密码',
      dataIndex: 'userPassword',
      formItemProps: {
        rules: [
          {
            min: 8,
            message: '密码长度不能小于8位',
          },
        ],
      },
      renderFormItem: () => <Input.Password/>,
    },
    {
      title: '用户头像',
      dataIndex: 'userAvatar',
    },
    {
      title: '用户角色',
      dataIndex: 'userRole',
      valueType: 'select',
      valueEnum: {
        0: {
          text: '普通用户',
          status: 'Default'
        },
        1: {
          text: '管理员',
          status: 'Success'
        },
      },
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      renderFormItem: (item, {value, onChange}, form) => (
        <Radio.Group value={form.getFieldValue(item.dataIndex)}>
          <Radio value={0}>普通用户</Radio>
          <Radio value={1}>管理员</Radio>
        </Radio.Group>
      ),
    },
  ];

  // return 返回单个 JSX(JavaScript + HTML) 元素
  return (
    <PageContainer>
      <ProTable<API.CurrentUser>
        columns={columns}
        actionRef={actionRef}
        cardBordered
        // @ts-ignore
        request={async (params, sort, filter) => {
          console.log(sort, filter);
          const userList = await getUserList({...params});
          return {
            data: userList,
          };
        }}
        editable={{
          type: 'multiple',
        }}
        columnsState={{
          persistenceKey: 'pro-table-singe-demos',
          persistenceType: 'localStorage',
          onChange(value) {
            console.log('value: ', value);
          },
        }}
        rowKey="id"
        search={{
          labelWidth: 'auto',
        }}
        options={{
          setting: {
            // @ts-ignore
            listsHeight: 400,
          },
        }}
        form={{
          // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
          syncToUrl: (values, type) => {
            if (type === 'get') {
              return {
                ...values,
                created_at: [values.startTime, values.endTime],
              };
            }
            return values;
          },
        }}
        pagination={{
          pageSize: 5,
          onChange: (page) => console.log(page),
        }}
        dateFormatter="string"
        headerTitle="用户管理"
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined/>}
            type="primary"
            onClick={() => {
              handleCreateModalVisible(true);
            }}
          >
            新建
          </Button>,
        ]}
      />
      <CreateModal
        columns={addUserColumns}
        onCancel={() => {
          handleCreateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleAddUser(values);
        }}
        visible={createModalVisible}
      />
      <UpdateModal
        columns={updateUserColumns}
        onCancel={() => {
          handleUpdateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleUpdateUser(values);
          actionRef.current?.reload();
        }}
        visible={updateModalVisible}
      />
    </PageContainer>
  );
};
// export 关键字使此函数可以在此文件之外访问
// default 关键字表明它是文件中的主要函数
export default UserManage;
