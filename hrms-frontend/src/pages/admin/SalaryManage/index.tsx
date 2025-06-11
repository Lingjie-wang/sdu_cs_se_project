import {PlusOutlined} from '@ant-design/icons';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {Button, message, Modal} from 'antd';
import React, {useRef, useState} from 'react';
import {addSalary, deleteSalary, getSalaryList, updateSalary} from "@/services/hr-pro/api";
import CreateModal from "@/pages/admin/SalaryManage/components/CreateModal";
import UpdateModal from "@/pages/admin/SalaryManage/components/UpdateModal";

const SalaryManage: React.FC = () => {
  // useState 当前的 state(createModalVisible) 用于更新它的函数(handleCreateModalVisible())
  // 第一次显示时 createModalVisible 为 false
  // 当想改变 state(createModalVisible) 的值时 调用 handleModalVisible() 将新值传给它
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, handleCurrentRow] = useState<API.Salary>();

  // 创建用户表单提交
  const handleAddSalary = async (fields: API.SalaryAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addSalary({
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

  const handleDeleteSalary = async (record: API.SalaryDeleteRequest) => {
    Modal.confirm({
      title: '你确定删除当前选中员工工资记录吗？',
      content: '删除这条员工工资记录后，系统员工工资记录数据不可恢复',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在删除');
        if (!record) return true;
        try {
          await deleteSalary({
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

  const handleUpdateSalary = async (fields: API.SalaryUpdateRequest) => {
    const hide = message.loading('正在更新');
    try {
      await updateSalary({
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

  const columns: ProColumns<API.Salary>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      align: 'center',
      search: false,
      //valueType: 'indexBorder',
    },
    {
      title: '员工编号',
      dataIndex: 'empId',
      align: 'center',
    },
    {
      title: '员工姓名',
      dataIndex: 'empName',
      align: 'center',
    },
    {
      title: '员工级别',
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
      title: '基本工资',
      dataIndex: 'wage',
      align: 'center',
    },
    {
      title: '奖金',
      dataIndex: 'bonus',
      align: 'center',
    },
    {
      title: '工资发放时间',
      dataIndex: 'createTime',
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
            handleDeleteSalary(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];

  const addSalaryColumns: ProColumns<API.SalaryAddRequest>[] = [
    {
      title: '员工编号',
      dataIndex: 'empId',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入员工编号',
          },
        ],
      },
    },
    {
      title: '员工姓名',
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
      title: '员工级别',
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
      title: '基本工资',
      dataIndex: 'wage',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入基本工资',
          },
        ],
      },
    },
    {
      title: '奖金',
      dataIndex: 'bonus',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入奖金',
          },
        ],
      },
    },
  ];

  const updateSalaryColumns: ProColumns<API.SalaryUpdateRequest>[] = [
    {
      title: '基本工资',
      dataIndex: 'wage',
    },
    {
      title: '奖金',
      dataIndex: 'bonus',
    },
    {
      title: '工资发放时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
    },
  ];

  // return 返回单个 JSX(JavaScript + HTML) 元素
  return (
    <PageContainer>
      <ProTable<API.Salary>
        columns={columns}
        actionRef={actionRef}
        cardBordered
        // @ts-ignore
        request={async (params, sort, filter) => {
          console.log(sort, filter);
          const salaryList = await getSalaryList({...params});
          return {
            data: salaryList,
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
        headerTitle="员工工资管理"
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
        columns={addSalaryColumns}
        onCancel={() => {
          handleCreateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleAddSalary(values);
        }}
        visible={createModalVisible}
      />
      <UpdateModal
        columns={updateSalaryColumns}
        onCancel={() => {
          handleUpdateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleUpdateSalary(values);
          actionRef.current?.reload();
        }}
        visible={updateModalVisible}
      />
    </PageContainer>
  );
};
// export 关键字使此函数可以在此文件之外访问
// default 关键字表明它是文件中的主要函数
export default SalaryManage;
