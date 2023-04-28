import React, { useState, useEffect, useRef } from 'react'
import { Button, Col, Form, Modal, Row, Table, Space, Popconfirm, Typography, Pagination } from 'antd'
import FormItem from "antd/es/form/FormItem"
import EditableCell from '../EditableCell'
import './index.css'

/**
 * 
 * @param {*} props

 * @returns 
 */
export default function CRUD(props) {
  const { columns = [], queryConfig = {}, rowKey = 'id', createConfig = false, updateConfig = false, deleteConfig = false, rowOperations = [], paginationConfig = false } = props
  const { conditions = [], doQuery, initQuery = true, onDatasourceChange = false } = queryConfig
  const { modalTitle = '', createFormItems = [], doCreate } = createConfig
  const { mode: updateMode = 'row', doUpdate, initUpdateFieldsValue = (record) => ({ ...record }) } = updateConfig
  const { mode: rowDeleteMode = 'row', doDelete, buttonName: deleteBtnName = '删除' } = deleteConfig
  const { totalName = 'total', contentName = 'contents', pageNoName = 'pageNo', pageSizeName = 'pageSize' } = paginationConfig
  const [queryForm] = Form.useForm()
  const [editForm] = Form.useForm()
  const [addForm] = Form.useForm()
  const [dataSource, setDataSource] = useState([])
  const [total, setTotal] = useState(0)
  const [openAdd, setOpenAdd] = useState(false)
  const [editingId, setEditingId] = useState('')
  const [selectRowKeys, setSelectRowKeys] = useState([])
  const [queryDivHeight, setQueryDivHeight] = useState(0)
  const [pageNo, setPageNo] = useState(1)
  const [pageSize, setPageSize] = useState(50)



  const batchDelete = rowDeleteMode === 'batch'
  const rowDelete = rowDeleteMode === 'row'

  const queryDivRef = useRef()

  useEffect(() => {
    if (typeof onDatasourceChange === 'function') {
      onDatasourceChange(dataSource)
    }
  }, [dataSource])

  useEffect(() => {
    queryData()
  }, [pageNo, pageSize])

  useEffect(() => {
    if (initQuery) {
      queryData()
    }
    setQueryDivHeight(queryDivRef.current.offsetHeight)
  }, [])

  const height = window.innerHeight - 64 - queryDivHeight;

  const rowSelection = batchDelete ? {
    type: 'checkbox',
    onChange: (selectRowKeys) => {
      setSelectRowKeys(selectRowKeys)
    }
  } : false

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

  const isRowUpdateMode = updateMode === 'row'
  const showOperationColumn = (isRowUpdateMode && updateConfig) || (rowDelete && deleteConfig) || rowOperations.length


  const editAbleColumns = columns.map(col => {
    if (col.editable && isRowUpdateMode) {
      col.onCell = (record) => ({
        record,
        editing: isEditing(record),
        name: col.dataIndex,
        component: col.component,
        rules: col.rules
      })
    }
    return col
  })



  if (showOperationColumn) {
    editAbleColumns.push({
      title: '操作',
      dataIndex: 'operations',
      render: (text, record, index) => {
        const editable = isEditing(record)
        const addtionalRowOperations = rowOperations.map((item, index) => {
          const { buttonName, onClick } = item
          const showButton = typeof buttonName === 'function' ? buttonName(record) : buttonName
          return (<Typography.Link key={index} onClick={() => onClick(record, dataSource, setDataSource)}>{showButton}</Typography.Link>
          )
        })

        const rowDeleteBtn = deleteConfig && rowDelete ?
          (<Popconfirm title="确定删除吗？" okText="确定" cancelText="取消" onConfirm={() => { onDelete(record) }}>
            <Typography.Link disabled={editingId !== ''} type="danger">{deleteBtnName}</Typography.Link>
          </Popconfirm>) : <></>
        const rowUpdateBtn = isRowUpdateMode ?
          (<Typography.Link disabled={editingId !== ''} onClick={() => onEdit(record)}>编辑</Typography.Link>) : <></>
        return editable ? (
          <Space>
            <Typography.Link onClick={() => onSaveEdit(record)}>保存</Typography.Link>
            <Popconfirm title="确定取消吗？" onConfirm={cancelEdit} okText="确定" cancelText="取消">
              <Typography.Link>取消</Typography.Link>
            </Popconfirm>
          </Space>
        ) : (
          <Space>
            {rowUpdateBtn}
            {rowDeleteBtn}
            {addtionalRowOperations}
          </Space>
        )
      }
    })
  }

  const onQueryClick = (e) => {
    queryData()
  }

  const queryData = () => {
    if (typeof doQuery === 'function') {
      queryForm.validateFields().then(data => {
        data = data || {}
        data[pageNoName] = pageNo
        data[pageSizeName] = pageSize
        return doQuery(data)
      }).then(res => {
        if (paginationConfig) {
          setDataSource(res[contentName])
          setTotal(res[totalName])
          setPageNo(res[pageNoName])
        } else {
          setDataSource(res)
        }
      }).catch(e => {  })
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

  const onDelete = (row) => {
    const rowId = row[rowKey]
    if (typeof doDelete === 'function') {
      doDelete(rowId)
        .then(res => queryData())
        .catch(err => { })
    }
  }

  const onBatchDelete = () => {
    if (selectRowKeys && selectRowKeys.length) {
      if (typeof doDelete === 'function') {
        doDelete(selectRowKeys)
          .then(res => queryData())
          .catch(err => { })
      }
    }
  }

  const onCreateModalCacel = () => {
    setOpenAdd(false)
  }

  const cancelEdit = () => setEditingId('')

  const isEditing = (record) => record[rowKey] === editingId

  const onEdit = (record) => {
    setEditingId(record[rowKey])
    editForm.setFieldsValue(initUpdateFieldsValue(record))
  }

  const onSaveEdit = (record) => {
    if (typeof doUpdate === 'function') {
      editForm.validateFields()
        .then(data => doUpdate(record, data))
        .then(_ => queryData())
        .then(_ => cancelEdit())
        .catch(e => { })
    }
  }
  const tableScrollY = height - 40 - (paginationConfig ? 32 : 0)
  const createBtn = createConfig ? <Button onClick={onCreateClick}>新增</Button> : <></>

  const deleteCount = selectRowKeys.length;
  const batchDeleteBtn = batchDelete ? (
    <Popconfirm title={'删除数据'} description={`确定要删除这${deleteCount}条记录吗？`} okText={'是的'} cancelText={'点错了'} onConfirm={() => { onBatchDelete() }} disabled={!deleteCount}>
      <Button danger type='primary' disabled={!selectRowKeys.length}>{deleteBtnName}</Button>
    </Popconfirm>
  ) : <></>

  const paginationContent = paginationConfig ? <Pagination
    total={total}
    showSizeChanger
    defaultCurrent={1}
    current={pageNo}
    defaultPageSize={pageSize}
    onChange={(page, perPage) => {
      setPageNo(page)
      setPageSize(perPage)
    }}
    showTotal={(total, range) => {
      return `共${total}条记录`
    }} /> : <></>
  return (
    <>
      <div ref={queryDivRef}>
        <Form form={queryForm}>
          <Row>
            {queryFormItems}
            <Button type='primary' onClick={onQueryClick} >查询</Button>
            <Button onClick={() => { queryForm.resetFields() }}>重置</Button>
            {createBtn}
            {batchDeleteBtn}
          </Row>
        </Form>
      </div>
      <Form form={editForm} className='table-form' style={{ height: height }}>
        <Table dataSource={dataSource}
          columns={editAbleColumns}
          bordered
          rowKey={rowKey}
          rowSelection={rowSelection}
          components={{
            body: {
              cell: EditableCell
            }
          }}
          pagination={false}
          size='small'
          scroll={{ y: `${tableScrollY}px`, scrollToFirstRowOnChange: true }}
        >
        </Table>
        {paginationContent}
      </Form>
      <Modal title={modalTitle} open={openAdd} onOk={onCreateModalConfirm} onCancel={onCreateModalCacel} okText={'确定'} cancelText='取消'>
        <Form form={addForm}>
          {addFormItems}
        </Form>
      </Modal>
    </>
  )
}
