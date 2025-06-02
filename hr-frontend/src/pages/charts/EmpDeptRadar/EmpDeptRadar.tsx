import React, {useEffect, useState} from 'react';
import {Radar} from '@ant-design/plots';

const EmpDeptRadar: React.FC = () => {
  const [value, setValue] = useState([]);

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
  const config = {
    data: value.data.empDeptChart,
    xField: 'deptName',
    yField: 'empCount',
    meta: {
      empCount: {
        alias: '员工人数',
        min: 0,
        max: 10,
        range: [0, 1],
      },
    },
    xAxis: {
      line: null,
      tickLine: null,
      grid: {
        line: {
          style: {
            lineDash: null,
          },
        },
      },
    },
    yAxis: {
      line: null,
      tickLine: null,
      grid: {
        line: {
          type: 'line',
          style: {
            lineDash: null,
          },
        },
        alternateColor: 'rgba(0, 0, 0, 0.04)',
      },
    },
    // 开启面积
    area: {},
    // 开启辅助点
    point: {
      size: 2,
    },
  };

  return <Radar {...config} />;
};
export default EmpDeptRadar;
