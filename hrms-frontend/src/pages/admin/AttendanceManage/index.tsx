import {PlusOutlined} from '@ant-design/icons';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {Button, message, Modal} from 'antd';
import React, {useRef, useState} from 'react';
import {addAttendance, deleteAttendance, getAttendanceList, updateAttendance} from "@/services/hr-pro/api";
import CreateModal from "@/pages/admin/AttendanceManage/components/CreateModal";
import UpdateModal from "@/pages/admin/AttendanceManage/components/UpdateModal";

const AttendanceManage: React.FC = () => {
  // useState 当前的 state(createModalVisible) 用于更新它的函数(handleCreateModalVisible())
  // 第一次显示时 createModalVisible 为 false
  // 当想改变 state(createModalVisible) 的值时 调用 handleModalVisible() 将新值传给它
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, handleCurrentRow] = useState<API.Attendance>();

  // 创建用户表单提交
  const handleAddAttendance = async (fields: API.AttendanceAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addAttendance({
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

  const handleDeleteAttendance = async (record: API.AttendanceDeleteRequest) => {
    Modal.confirm({
      title: '你确定删除当前选中员工考勤记录吗？',
      content: '删除这条员工考勤记录后，系统员工考勤记录数据不可恢复',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在删除');
        if (!record) return true;
        try {
          await deleteAttendance({
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

  const handleUpdateAttendance = async (fields: API.AttendanceUpdateRequest) => {
    const hide = message.loading('正在更新');
    try {
      await updateAttendance({
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

  const columns: ProColumns<API.Attendance>[] = [
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
      title: '考勤日期',
      dataIndex: 'attendanceDate',
      valueType: 'dateTime',
      align: 'center',
      search: false,
    },
    {
      title: '考勤类型',
      dataIndex: 'attendanceType',
      align: 'center',
      valueType: 'select',
      valueEnum: {
        0: {
          text: '正常',
          status: 'Success',
        },
        1: {
          text: '迟到',
          status: 'Default',
        },
        2: {
          text: '缺勤',
          status: 'Error',
        },
        3: {
          text: '请假',
          status: 'Processing',
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
            handleDeleteAttendance(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];

  const addAttendanceColumns: ProColumns<API.AttendanceAddRequest>[] = [
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
      title: '考勤类型',
      dataIndex: 'attendanceType',
      valueType: 'select',
      valueEnum: {
        0: {
          text: '正常',
        },
        1: {
          text: '迟到',
        },
        2: {
          text: '缺勤',
        },
        3: {
          text: '请假',
        },
      }
    },
  ];

  const updateAttendanceColumns: ProColumns<API.AttendanceUpdateRequest>[] = [
    {
      title: '考勤日期',
      dataIndex: 'attendanceDate',
      valueType: 'dateTime',
    },
    {
      title: '考勤类型',
      dataIndex: 'attendanceType',
      valueType: 'select',
      valueEnum: {
        0: {
          text: '正常',
        },
        1: {
          text: '迟到',
        },
        2: {
          text: '缺勤',
        },
        3: {
          text: '请假',
        },
      }
    },
  ];

  // return 返回单个 JSX(JavaScript + HTML) 元素
  return (
    <PageContainer>
      <ProTable<API.Attendance>
        columns={columns}
        actionRef={actionRef}
        cardBordered
        // @ts-ignore
        request={async (params, sort, filter) => {
          console.log(sort, filter);
          const attendanceList = await getAttendanceList({...params});
          return {
            data: attendanceList,
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
        headerTitle="员工考勤管理"
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
        columns={addAttendanceColumns}
        onCancel={() => {
          handleCreateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleAddAttendance(values);
        }}
        visible={createModalVisible}
      />
      <UpdateModal
        columns={updateAttendanceColumns}
        onCancel={() => {
          handleUpdateModalVisible(false);
        }}
        // @ts-ignore
        onSubmit={(values) => {
          handleUpdateAttendance(values);
          actionRef.current?.reload();
        }}
        visible={updateModalVisible}
      />
    </PageContainer>
  );
};
// export 关键字使此函数可以在此文件之外访问
// default 关键字表明它是文件中的主要函数
export default AttendanceManage;
