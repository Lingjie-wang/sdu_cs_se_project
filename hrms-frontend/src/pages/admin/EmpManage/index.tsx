import {PlusOutlined} from '@ant-design/icons';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {Button, message, Modal, Radio} from 'antd';
import React, {useRef, useState} from 'react';
import {addEmployee, deleteEmployee, getEmployeeList, updateEmployee} from "@/services/hr-pro/api";
import CreateModal from "@/pages/admin/EmpManage/components/CreateModal";
import UpdateModal from "@/pages/admin/EmpManage/components/UpdateModal";

const EmpManage: React.FC = () => {
  // useState 当前的 state(createModalVisible) 用于更新它的函数(handleCreateModalVisible())
  // 第一次显示时 createModalVisible 为 false
  // 当想改变 state(createModalVisible) 的值时 调用 handleModalVisible() 将新值传给它
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, handleCurrentRow] = useState<API.Employee>();

  // 创建用户表单提交
  const handleAddEmployee = async (fields: API.EmployeeAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addEmployee({
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

  const handleDeleteEmployee = async (record: API.EmployeeDeleteRequest) => {
    Modal.confirm({
      title: '你确定删除当前选中员工吗？',
      content: '系统仍将保留该员工数据，以备日后查询',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在删除');
        if (!record) return true;
        try {
          await deleteEmployee({
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

  const handleUpdateEmployee = async (fields: API.EmployeeUpdateRequest) => {
    const hide = message.loading('正在更新');
    try {
      await updateEmployee({
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

  const columns: ProColumns<API.Employee>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      align: 'center',
      search: false,
      //valueType: 'indexBorder',
    },
    {
      title: '姓名',
      dataIndex: 'empName',
      align: 'center',
      copyable: true,
    },
    {
      title: '性别',
      dataIndex: 'empGender',
      valueType: 'select',
      align: 'center',
      valueEnum: {
        0: {
          text: '男',
        },
        1: {
          text: '女',
        },
      },
    },
    {
      title: '邮箱',
      dataIndex: 'empEmail',
      align: 'center',
      search: false,
    },
    {
      title: '电话',
      dataIndex: 'empPhone',
      align: 'center',
      search: false,
    },
    {
      title: '级别',
      dataIndex: 'postId',
      align: 'center',
      valueType: 'select',
      valueEnum: {
        1: {
          text: '总经理',
        },
        2: {
          text: '部门经理',
        },
        3: {
          text: '项目组组长',
        },
        4: {
          text: '普通员工',
        },
        5: {
          text: '试用员工',
        },
      },
    },
    {
      title: '部门',
      dataIndex: 'deptId',
      align: 'center',
      valueType: 'select',
      valueEnum: {
        1: {
          text: '市场部',
        },
        2: {
          text: '研发部',
        },
        3: {
          text: '销售部',
        },
        4: {
          text: '财务部',
        },
        5: {
          text: '生产部',
        },
        6: {
          text: '人事部',
        },
      },
    },
    {
      title: '入职时间',
      dataIndex: 'hireDate',
      valueType: 'dateTime',
      align: 'center',
      search: false,
    },
    {
      title: '离职时间',
      dataIndex: 'resignDate',
      valueType: 'dateTime',
      align: 'center',
      search: false,
    },
    {
      title: '工作状态',
      dataIndex: 'empStatus',
      valueType: 'select',
      align: 'center',
      valueEnum: {
        0: {
          text: '在职',
          status: 'Success',
        },
        1: {
          text: '已离职',
          status: 'Default',
        },
      },
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
            handleDeleteEmployee(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];

  const addEmployeeColumns: ProColumns<API.EmployeeAddRequest>[] = [
    {
      title: '姓名',
      dataIndex: 'empName',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入员工姓名',
          },
        ],
      },
    },
    {
      title: '性别',
      dataIndex: 'empGender',
      valueType: 'select',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择员工性别',
          },
        ],
      },
      valueEnum: {
        0: {
          text: '男',
          status: 'Default'
        },
        1: {
          text: '女',
          status: 'Success'
        },
      },
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      renderFormItem: (item, {value, onChange}, form) => (
        <Radio.Group value={form.getFieldValue(item.dataIndex)}>
          <Radio value={0}>男</Radio>
          <Radio value={1}>女</Radio>
        </Radio.Group>
      ),
    },
    {
      title: '邮箱',
      dataIndex: 'empEmail',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入员工邮箱',
          },
        ],
      },
    },
    {
      title: '电话',
      dataIndex: 'empPhone',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入员工电话',
          },
        ],
      },
    },
    {
      title: '级别',
      dataIndex: 'postTitle',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入员工级别',
          },
        ],
      },
    },
    {
      title: '部门',
      dataIndex: 'deptName',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入员工部门',
          },
        ],
      },
    },
  ];

  const updateEmployeeColumns: ProColumns<API.EmployeeUpdateRequest>[] = [
    {
      title: '姓名',
      dataIndex: 'empName',
    },
    {
      title: '性别',
      dataIndex: 'empGender',
      valueType: 'select',
      valueEnum: {
        0: {
          text: '男',
          status: 'Default'
        },
        1: {
          text: '女',
          status: 'Success'
        },
      },
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      renderFormItem: (item, {value, onChange}, form) => (
        <Radio.Group value={form.getFieldValue(item.dataIndex)}>
          <Radio value={0}>男</Radio>
          <Radio value={1}>女</Radio>
        </Radio.Group>
      ),
    },
    {
      title: '邮箱',
      dataIndex: 'empEmail',
    },
    {
      title: '电话',
      dataIndex: 'empPhone',
    },
    {
      title: '级别',
      dataIndex: 'postTitle',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择员工级别',
          },
        ],
      },
    },
    {
      title: '部门',
      dataIndex: 'deptName',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择员工部门',
          },
        ],
      },
    },
    {
      title: '离职时间',
      dataIndex: 'resignDate',
      valueType: 'dateTime',
    },
  ];

  // return 返回单个 JSX(JavaScript + HTML) 元素
  return (
    <PageContainer>
      <ProTable<API.Employee>
        columns={columns}
        actionRef={actionRef}
        cardBordered
        // @ts-ignore
        request={async (params, sort, filter) => {
          console.log(sort, filter);
          const empList = await getEmployeeList({...params});
          return {
            data: empList,
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
        headerTitle="员工管理"
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
        columns={addEmployeeColumns}
        onCancel={() => {
          handleCreateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleAddEmployee(values);
        }}
        visible={createModalVisible}
      />
      <UpdateModal
        columns={updateEmployeeColumns}
        onCancel={() => {
          handleUpdateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleUpdateEmployee(values);
          actionRef.current?.reload();
        }}
        visible={updateModalVisible}
      />
    </PageContainer>
  );
};
// export 关键字使此函数可以在此文件之外访问
// default 关键字表明它是文件中的主要函数
export default EmpManage;
