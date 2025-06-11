import {PageContainer, ProCard, StatisticCard} from '@ant-design/pro-components';
import RcResizeObserver from 'rc-resize-observer';
import React, {useEffect, useState} from 'react';
import EmpDeptRadar from "@/pages/charts/EmpDeptRadar/EmpDeptRadar";
import EmpPostRadar from "@/pages/charts/EmpPostRadar/EmpPostRadar";
import AttendancePie from "@/pages/charts/AttendancePie/AttendancePie";

const Welcome: React.FC = () => {
  const [responsive, setResponsive] = useState(false);
  const [value, setValue] = useState({});

  useEffect(() => {
    // eslint-disable-next-line @typescript-eslint/no-use-before-define
    asyncFetch();
  }, []);

  const asyncFetch = () => {
    fetch('http://localhost:8080/api/charts/data')
      .then((response) => response.json())
      .then((json) => setValue(json))
      .catch((error) => {
        console.log('fetch data failed', error);
      });
  };
  if (!Object.keys(value).length) {
    return null;
  }
  return (
    <PageContainer title={'报表中心'}>
      <RcResizeObserver
        key="resize-observer"
        onResize={(offset: { width: number; }) => {
          setResponsive(offset.width < 596);
        }}
      >
        <ProCard
          title="数据概览"
          extra={new Date().toLocaleDateString()}
          split={responsive ? 'horizontal' : 'vertical'}
          headerBordered
          bordered
        >
          <ProCard split="horizontal">
            <ProCard split="horizontal">
              <ProCard split="vertical">
                <StatisticCard
                  statistic={{
                    title: '员工总数',
                    value: value.data.empCount,
                  }}
                />
                <StatisticCard
                  statistic={{
                    title: '级别总数',
                    value: value.data.postCount,
                  }}
                />
              </ProCard>
            </ProCard>
            <StatisticCard
              title="各级别员工数"
              chart={
                <EmpPostRadar/>
              }
            />
          </ProCard>

          <ProCard split="horizontal">
            <ProCard split="horizontal">
              <ProCard split="vertical">
                <StatisticCard
                  statistic={{
                    title: '员工总数',
                    value: value.data.empCount,
                  }}
                />
                <StatisticCard
                  statistic={{
                    title: '部门总数',
                    value: value.data.deptCount,
                  }}
                />
              </ProCard>
            </ProCard>
            <StatisticCard
              title="各部门员工数"
              chart={
                <EmpDeptRadar/>
              }
            />
          </ProCard>

          <ProCard split="horizontal">
            <ProCard split="horizontal">
              <ProCard split="vertical">
                <StatisticCard
                  statistic={{
                    title: '考勤类型',
                    value: 4,
                  }}
                />
                <StatisticCard
                  statistic={{
                    title: '员工考勤总数',
                    value: value.data.attendanceCount,
                  }}
                />
              </ProCard>
            </ProCard>
            <StatisticCard
              title="员工考勤"
              chart={
                <AttendancePie/>
              }
            />
          </ProCard>

        </ProCard>
      </RcResizeObserver>
    </PageContainer>
  );
};
export default Welcome;
