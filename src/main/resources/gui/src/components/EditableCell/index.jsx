import React from 'react'
import { Form } from 'antd'

export default function EditableCell(props) {
  const {
    editing,
    name,
    record,
    children,
    rules,
    component,
    ...restProps
  } = props
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
          {component}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  )
}
