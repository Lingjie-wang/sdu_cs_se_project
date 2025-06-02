import type {ProColumns} from '@ant-design/pro-components';
import {ProTable} from '@ant-design/pro-components';
import {Modal} from 'antd';
import React from 'react';

export type Props = {
  columns: ProColumns<API.PositionUpdateRequest>[];
  onCancel: () => void;
  onSubmit: (values: API.PositionUpdateRequest) => Promise<void>;
  visible: boolean;
};

// React 自定义组件名必须以大写字母开头
// () => {} 箭头函数 定义函数的一种方式
const UpdateModal: React.FC<Props> = (props) => {

  // props 传递数据(参数) 父组件传递数据给子组件
  const {visible, columns, onCancel, onSubmit} = props;

  return (
    <Modal
      title="级别更新"
      visible={visible}
      footer={null}
      onCancel={() => onCancel?.()}
    >
      <ProTable
        type="form"
        columns={columns}
        onSubmit={async (value) => {
          onSubmit?.(value);
        }}
      />
    </Modal>
  );
};
export default UpdateModal;
