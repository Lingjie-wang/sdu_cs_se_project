import type {ProFormColumnsType} from '@ant-design/pro-components';
import {BetaSchemaForm, PageContainer} from '@ant-design/pro-components';
import {message} from "antd";
import {addAttendance} from "@/services/hr-pro/api";
import React from "react";

const CheckIn: React.FC = () => {
  const handleAddAttendance = async (fields: API.AttendanceAddRequest) => {
    const hide = message.loading('正在添加');
    try {
      await addAttendance({
        ...fields,
      });
      hide();
      message.success('创建成功');
      return true;
    } catch (error: any) {
      hide();
      message.error('创建失败，' + error.message);
      return false;
    }
  };

  const columns: ProFormColumnsType<API.AttendanceAddRequest>[][] = [
    [
      {
        title: '员工编号',
        dataIndex: 'empId',
        formItemProps: {
          rules: [
            {
              required: true,
              message: '此项为必填项',
            },
          ],
        },
        width: 'm',
      },
      {
        title: '员工姓名',
        dataIndex: 'empName',
        formItemProps: {
          rules: [
            {
              required: true,
              message: '此项为必填项',
            },
          ],
        },
        width: 'm',
      },
    ],
    [
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
        },
        formItemProps: {
          rules: [
            {
              required: true,
              message: '此项为必填项',
            },
          ],
        },
        width: 'm',
        tooltip: '每天只能选择一种情况进行签到',
      },
    ],
    [
      {
        title: '考勤时间',
        dataIndex: 'attendanceDate',
        valueType: 'dateTime',
        width: 'm',
      },
    ],
  ];

  return (
    <PageContainer
      title={"员工考勤打卡"}
    >
      <BetaSchemaForm<API.AttendanceAddRequest>
        layoutType="StepsForm"
        steps={[
          {
            title: '第一步',
          },
          {
            title: '第二步',
          },
          {
            title: '第三步',
          },
        ]}
        onFinish={async (values) => {
          await handleAddAttendance(values);
        }}
        columns={columns}
      />
    </PageContainer>
  );
}
export default CheckIn;


