import {PlusOutlined} from '@ant-design/icons';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {Button, message, Modal} from 'antd';
import React, {useRef, useState} from 'react';
import {addDepartment, deleteDepartment, getDepartmentList, updateDepartment} from "@/services/hr-pro/api";
import CreateModal from "@/pages/admin/DeptManage/components/CreateModal";
import UpdateModal from "@/pages/admin/DeptManage/components/UpdateModal";

const DeptManage: React.FC = () => {
  // useState 当前的 state(createModalVisible) 用于更新它的函数(handleCreateModalVisible())
  // 第一次显示时 createModalVisible 为 false
  // 当想改变 state(createModalVisible) 的值时 调用 handleModalVisible() 将新值传给它
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, handleCurrentRow] = useState<API.Department>();

  // 创建用户表单提交
  const handleAddDepartment = async (fields: API.DepartmentAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addDepartment({
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

  const handleDeleteDepartment = async (record: API.DepartmentDeleteRequest) => {
    Modal.confirm({
      title: '你确定删除当前选中部门吗？',
      content: '删除这条部门信息后，系统部门数据不可恢复',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在删除');
        if (!record) return true;
        try {
          await deleteDepartment({
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

  const handleUpdateDepartment = async (fields: API.DepartmentUpdateRequest) => {
    const hide = message.loading('正在更新');
    try {
      await updateDepartment({
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

  const columns: ProColumns<API.Department>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      align: 'center',
      search: false,
      //valueType: 'indexBorder',
    },
    {
      title: '部门名称',
      dataIndex: 'name',
      align: 'center',
      copyable: true,
    },
    {
      title: '部门职责',
      dataIndex: 'responsibility',
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
            handleDeleteDepartment(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];

  const addDepartmentColumns: ProColumns<API.DepartmentAddRequest>[] = [
    {
      title: '部门名称',
      dataIndex: 'name',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入部门名称',
          },
        ],
      },
    },
    {
      title: '部门职责',
      dataIndex: 'responsibility',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入部门职责',
          },
        ],
      },
    },
  ];

  const updateDepartmentColumns: ProColumns<API.DepartmentUpdateRequest>[] = [
    {
      title: '部门名称',
      dataIndex: 'name',
    },
    {
      title: '部门职责',
      dataIndex: 'responsibility',
    },
  ];

  // return 返回单个 JSX(JavaScript + HTML) 元素
  return (
    <PageContainer>
      <ProTable<API.Department>
        columns={columns}
        actionRef={actionRef}
        cardBordered
        // @ts-ignore
        request={async (params, sort, filter) => {
          console.log(sort, filter);
          const deptList = await getDepartmentList({...params});
          return {
            data: deptList,
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
        headerTitle="部门管理"
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
        columns={addDepartmentColumns}
        onCancel={() => {
          handleCreateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleAddDepartment(values);
        }}
        visible={createModalVisible}
      />
      <UpdateModal
        columns={updateDepartmentColumns}
        onCancel={() => {
          handleUpdateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleUpdateDepartment(values);
          actionRef.current?.reload();
        }}
        visible={updateModalVisible}
      />
    </PageContainer>
  );
};
// export 关键字使此函数可以在此文件之外访问
// default 关键字表明它是文件中的主要函数
export default DeptManage;
