import React from 'react'
import {InputNumber, Select, Input, Form} from 'antd'

export default function EditableCell(props) {
    const {
        editing,
        name,
        dataType,
        record,
        children,
        selectoptions,
        rules,
        ...restProps
    } = props
    const inputNode = dataType === 'number' ?
    <InputNumber /> : dataType === 'select' ?
      <Select options={selectoptions} /> : <Input />;
    return (
        <td {...restProps}>
      {editing ? (
        <Form.Item
          name={name}
          style={{
            margin: 0,
          }}
          rules={rules}
        >
          {inputNode}
        </Form.Item>
      ) : (
        children
      )}
    </td>
    )
}
