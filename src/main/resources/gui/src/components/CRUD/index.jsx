import React, { useState } from 'react'
import { Button, Col, Form, Modal, Row, Table, Space, Popconfirm, Typography } from 'antd'
import FormItem from "antd/es/form/FormItem"
import EditableCell from '../EditableCell'

/**
 * 
 * @param {*} props
 * {
 *  "columns": 
 * 
 * }
 * 
 * 
 *  
 * @returns 
 */
export default function CRUD(props) {
  const { config } = props
  const { columns, query, rowKey = 'id', create, update, deleteRow } = config
  const { conditions = [], doQuery } = query
  const { modalTitle = '', createFormItems = [], doCreate } = create
  const { doUpdate } = update
  const [queryForm] = Form.useForm()
  const [editForm] = Form.useForm()
  const [addForm] = Form.useForm()
  const [dataSource, setDataSource] = useState([])
  const [openAdd, setOpenAdd] = useState(false)
  const [editingId, setEditingId] = useState('')


  const queryFormItems = conditions.map((item, index) => (
    <Col span={item.span || 6} key={index}>
      <FormItem key={index} label={item.label} name={item.name} rules={item.rules}>
        {item.component}
      </FormItem>
    </Col>
  ))

  const addFormItems = createFormItems.map((item, index) => {
    const { label, name, rules, component } = item
    return (
      <FormItem key={index} label={label} name={name} rules={rules} labelCol={{ span: 4 }} wrapperCol={{ span: 16 }}>
        {component}
      </FormItem>
    )
  })


  const editAbleColumns = [...columns.map(col => {
    if (col.editable) {
      col.onCell = (record) => ({
        record,
        editing: isEditing(record),
        name: col.dataIndex,
        component: col.component
      })
    }
    return col
  }), {
    title: '操作',
    dataIndex: 'operations',
    render: (text, record, index) => {
      const editable = isEditing(record)
      return editable ? (
        <Space>
          <Typography.Link onClick={() => onSaveEdit(record)}>保存</Typography.Link>
          <Popconfirm title="确定取消吗？" onConfirm={cancelEdit} okText="确定" cancelText="取消">
            <Typography.Link>取消</Typography.Link>
          </Popconfirm>
        </Space>
      ) : (
        <Space>
          <Typography.Link disabled={editingId !== ''} onClick={() => onEdit(record)}>编辑</Typography.Link>
          <Popconfirm title="确定删除吗？" okText="确定" cancelText="取消" onConfirm={() => { }}>
            <Typography.Link disabled={editingId !== ''} type="danger">删除</Typography.Link>
          </Popconfirm>
        </Space>
      )
    }
  }]


  const onQueryClick = (e) => {
    queryData()
  }

  const queryData = () => {
    if (typeof doQuery === 'function') {
      queryForm.validateFields().then(data => doQuery(data).then(dataList => setDataSource(dataList))).catch(e => { })
    }
  }

  const onCreateClick = (e) => {
    addForm.resetFields()
    setOpenAdd(true)
  }

  const onCreateModalConfirm = (e) => {
    if (typeof doCreate === 'function') {
      addForm.validateFields()
        .then(data => doCreate(data))
        .then(_ => {
          queryData()
          onCreateModalCacel()
        })
        .catch(e => { })

    }
  }

  const onCreateModalCacel = () => {
    setOpenAdd(false)
  }

  const cancelEdit = () => setEditingId('')

  const isEditing = (record) => record[rowKey] == editingId

  const onEdit = (record) => {
    setEditingId(record[rowKey])
    editForm.setFieldsValue({ ...record })
  }

  const onSaveEdit = (record) => {
    if (typeof doUpdate === 'function') {
      editForm.validateFields()
        .then(data =>  doUpdate(record, data))
        .then(_ => queryData())
        .then(_ => cancelEdit())
        .catch(e => { })
    }
  }

  const createBtn = create ? <Button onClick={onCreateClick}>新增</Button> : <></>
  return (
    <>
      <Form form={queryForm}>
        <Row>
          {queryFormItems}
          <Button type='primary' onClick={onQueryClick} >查询</Button>
          <Button>重置</Button>
          {createBtn}
        </Row>
      </Form>
      <Form form={editForm}>
        <Table dataSource={dataSource}
          columns={editAbleColumns}
          bordered
          rowKey={rowKey}
          components={{
            body: {
              cell: EditableCell
            }
          }}
        ></Table>
      </Form>
      <Modal title={modalTitle} open={openAdd} onOk={onCreateModalConfirm} onCancel={onCreateModalCacel}>
        <Form form={addForm}>
          {addFormItems}
        </Form>
      </Modal>
    </>
  )
}
