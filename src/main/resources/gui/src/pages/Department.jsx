import React from 'react'
import { Input,InputNumber } from 'antd'
import { formatDate } from '../utils/date_util'
import CRUD from '../components/CRUD'


export default function Department() {
  const columns = [
    {
        title: '部门名称',
        dataIndex: 'departmentName',
        editable: true,
        component: <Input />,
        rules: [
            {
                required: true,
                message: '部门名称必填',
            },
        ]
    },
    {
        title: '部门描述',
        dataIndex: 'departmentDescription',
        editable: true,
        component: <Input />,
    },
    {
        title: '负责人',
        dataIndex: 'managerId',
        editable: true,
        component: <Input />,
    },
    {
        title: '创建时间',
        dataIndex: 'createAt',
        render: (text) => formatDate(text)
    }]


  return (
    <CRUD
          columns={columns}
          // queryConfig={queryConfig}
          // updateConfig={updateConfig}
          // deleteConfig={deleteConfig}
          // createConfig={createConfig}
      />
  )
}
