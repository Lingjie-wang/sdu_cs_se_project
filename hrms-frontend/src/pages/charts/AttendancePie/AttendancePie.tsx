import React, {useEffect, useState} from 'react';
import {Pie} from "@ant-design/plots";

const AttendancePie: React.FC = () => {
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
    appendPadding: 10,
    data: value.data.attendanceChart,
    angleField: 'attendanceCount',
    colorField: 'attendanceTypeName',
    radius: 0.75,
    label: {
      type: 'spider',
      labelHeight: 28,
      content: '{name}\n{percentage}',
    },
    interactions: [
      {
        type: 'element-selected',
      },
      {
        type: 'element-active',
      },
    ],
  };

  return <Pie {...config} />;
};
export default AttendancePie;
