import { Form, InputNumber, Popconfirm, Table, Typography, Input, Select } from 'antd';
import { useState, useEffect } from 'react';
import { getResourceTree } from '../service/resource';
import { RESOURCE_TYPES, RESOURCE_OPTIONS } from '../constants/code_mapping';

const queryList = () => {
  return getResourceTree();
}

const EditableCell = ({
  editing,
  dataIndex,
  title,
  dataType,
  record,
  index,
  children,
  selectoptions,
  rules,
  ...restProps
}) => {
  const inputNode = dataType === 'number' ?
    <InputNumber /> : dataType === 'select' ?
      <Select options={selectoptions} /> : <Input />;
  return (
    <td {...restProps}>
      {editing ? (
        <Form.Item
          name={dataIndex}
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
  );
};

const Menu = () => {
  const [form] = Form.useForm();
  const [resources, setResources] = useState([])
  const [editingId, setEditingId] = useState('')
  const isEditing = (row) => row.id === editingId
  const cancel = () => {
    setEditingId('');
  };
  const save = () => { }
  const edit = (record) => {
    form.setFieldsValue({
      ...record,
    });
    setEditingId(record.id);
  }

  const columns = [
    {
      title: '资源编码',
      dataIndex: 'resourceCode',
      key: 'resourceCode',
      editable: true,
      dataType: 'text',
      rules: [
        {
          required: true,
          message: '资源编码必填',
        },
      ]
    },
    {
      title: '资源名称',
      dataIndex: 'resourceName',
      key: 'resourceName',
      editable: true,
      dataType: 'text',
    },
    {
      title: '资源类型',
      dataIndex: 'resourceType',
      key: 'resourceType',
      editable: true,
      render: type => RESOURCE_TYPES[type],
      dataType: 'select',
      selectoptions: RESOURCE_OPTIONS
    },
    {
      title: '菜单路由',
      dataIndex: 'resourceUrl',
      key: 'resourceUrl',
      editable: true,
      dataType: 'text',
    },
    {
      title: '菜单图标',
      dataIndex: 'icon',
      key: 'icon',
      editable: true,
      dataType: 'text',
    },
    {
      title: '菜单排序',
      dataIndex: 'sort',
      key: 'sort',
      editable: true,
      dataType: 'number',
    },
    {
      title: '创建时间',
      dataIndex: 'createAt',
      key: 'createAt',
      dataType: 'datetime',
    },
    {
      title: '操作',
      dataIndex: 'operation',
      key: 'operation',
      render: (text, record, index) => {
        const editable = isEditing(record)
        return editable ? (
          <span>
            <Typography.Link
              onClick={() => save(record.key)}
              style={{
                marginRight: 8,
              }}
            >
              保存
            </Typography.Link>
            <Popconfirm title="确定取消吗？" onConfirm={cancel} okText="确定" cancelText="取消">
              <a>取消</a>
            </Popconfirm>
          </span>
        ) : (
          <Typography.Link disabled={editingId !== ''} onClick={() => edit(record)}>
            编辑
          </Typography.Link>
        );
      }
    },
  ];

  const mergedColumns = columns.map((col) => {
    if (!col.editable) {
      return col;
    }
    return {
      ...col,
      onCell: (record) => {
        return {
          record,
          dataIndex: col.dataIndex,
          title: col.title,
          dataType: col.dataType,
          selectoptions: col.selectoptions,
          rules:col.rules,
          editing: isEditing(record),
        }
      },
    };
  });

  useEffect(() => {
    queryList()
      .then(res => setResources(res))
      .catch(err => { })
  }, [])

  return (
    <Form form={form} component={false}>
      <Table
        columns={mergedColumns}
        bordered
        components={{
          body: {
            cell: EditableCell,
          },
        }}
        // rowSelection={{
        //   // ...rowSelection,
        //   checkStrictly: false,
        // }}
        dataSource={resources}
        rowKey="id"
      />
    </Form>
  );
};
export default Menu;