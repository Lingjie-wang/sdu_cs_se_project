import {PlusOutlined} from '@ant-design/icons';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {Button, message, Modal} from 'antd';
import React, {useRef, useState} from 'react';
import {addTraining, deleteTraining, getTrainingList, updateTraining} from "@/services/hr-pro/api";
import CreateModal from "@/pages/admin/TrainManage/components/CreateModal";
import UpdateModal from "@/pages/admin/TrainManage/components/UpdateModal";

const TrainManage: React.FC = () => {
  // useState 当前的 state(createModalVisible) 用于更新它的函数(handleCreateModalVisible())
  // 第一次显示时 createModalVisible 为 false
  // 当想改变 state(createModalVisible) 的值时 调用 handleModalVisible() 将新值传给它
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, handleCurrentRow] = useState<API.Training>();

  // 创建用户表单提交
  const handleAddTraining = async (fields: API.TrainingAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addTraining({
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

  const handleDeleteTraining = async (record: API.TrainingDeleteRequest) => {
    Modal.confirm({
      title: '你确定删除当前选中员工培训记录吗？',
      content: '删除这条员工培训记录后，系统员工培训记录数据不可恢复',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在删除');
        if (!record) return true;
        try {
          await deleteTraining({
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

  const handleUpdateTraining = async (fields: API.TrainingUpdateRequest) => {
    const hide = message.loading('正在更新');
    try {
      await updateTraining({
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

  const columns: ProColumns<API.Training>[] = [
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
      title: '培训项目',
      dataIndex: 'trainingProject',
      align: 'center',
    },
    {
      title: '培训日期',
      dataIndex: 'trainingDate',
      align: 'center',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '培训时长',
      dataIndex: 'trainingDuration',
      align: 'center',
    },
    {
      title: '培训讲师',
      dataIndex: 'trainer',
      align: 'center',
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
            handleDeleteTraining(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];

  const addTrainingColumns: ProColumns<API.TrainingAddRequest>[] = [
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
      title: '培训项目',
      dataIndex: 'trainingProject',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入培训项目',
          },
        ],
      },
    },
    {
      title: '培训时长',
      dataIndex: 'trainingDuration',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入培训时长',
          },
        ],
      },
    },
    {
      title: '培训讲师',
      dataIndex: 'trainer',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入培训讲师',
          },
        ],
      },
    },
  ];

  const updateTrainingColumns: ProColumns<API.TrainingUpdateRequest>[] = [
    {
      title: '培训项目',
      dataIndex: 'trainingProject',
    },
    {
      title: '培训日期',
      dataIndex: 'trainingDate',
      valueType: 'dateTime',
    },
    {
      title: '培训时长',
      dataIndex: 'trainingDuration',
    },
    {
      title: '培训讲师',
      dataIndex: 'trainer',
    },
  ];

  // return 返回单个 JSX(JavaScript + HTML) 元素
  return (
    <PageContainer>
      <ProTable<API.Training>
        columns={columns}
        actionRef={actionRef}
        cardBordered
        // @ts-ignore
        request={async (params, sort, filter) => {
          console.log(sort, filter);
          const trainingList = await getTrainingList({...params});
          return {
            data: trainingList,
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
        headerTitle="员工培训管理"
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
        columns={addTrainingColumns}
        onCancel={() => {
          handleCreateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleAddTraining(values);
        }}
        visible={createModalVisible}
      />
      <UpdateModal
        columns={updateTrainingColumns}
        onCancel={() => {
          handleUpdateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleUpdateTraining(values);
          actionRef.current?.reload();
        }}
        visible={updateModalVisible}
      />
    </PageContainer>
  );
};
// export 关键字使此函数可以在此文件之外访问
// default 关键字表明它是文件中的主要函数
export default TrainManage;
