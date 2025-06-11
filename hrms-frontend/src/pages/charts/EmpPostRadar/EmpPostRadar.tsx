import React, {useEffect, useState} from 'react';
import {Radar} from '@ant-design/plots';

/**
 * value 状态变量获取的从后端请求到的数据
 *
 * {
 *     "code": 0,
 *     "data": {
 *         "empCount": 13,
 *         "deptCount": 6,
 *         "empDeptChart": [
 *             {
 *                 "deptName": "市场部",
 *                 "empCount": 5
 *             },
 *             {
 *                 "deptName": "研发部",
 *                 "empCount": 2
 *             },
 *             {
 *                 "deptName": "销售部",
 *                 "empCount": 1
 *             },
 *             {
 *                 "deptName": "财务部",
 *                 "empCount": 1
 *             },
 *             {
 *                 "deptName": "生产部",
 *                 "empCount": 2
 *             },
 *             {
 *                 "deptName": "人事部",
 *                 "empCount": 2
 *             }
 *         ],
 *         "postCount": 5,
 *         "empPostChart": [
 *             {
 *                 "postTitle": "总经理",
 *                 "empCount": 2
 *             },
 *             {
 *                 "postTitle": "部门经理",
 *                 "empCount": 2
 *             },
 *             {
 *                 "postTitle": "项目组组长",
 *                 "empCount": 3
 *             },
 *             {
 *                 "postTitle": "普通员工",
 *                 "empCount": 4
 *             },
 *             {
 *                 "postTitle": "试用员工",
 *                 "empCount": 2
 *             }
 *         ]
 *     },
 *     "message": "成功"
 * }
 *
 */

const EmpPostRadar: React.FC = () => {
  // value 状态变量 初始值为空数组 存储组件内部的状态数据
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
    // 雷达图配置中的 data 需要一个数组
    data: value.data.empPostChart,
    xField: 'postTitle',
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
export default EmpPostRadar;
