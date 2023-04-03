import { Form, InputNumber, Popconfirm, Table, Typography, Input, Select, Button, Space, Tooltip, Modal, TreeSelect } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { useState, useEffect } from 'react';
import { getResourceTree, updateResource , addResource} from '../service/resource';
import { RESOURCE_TYPES, RESOURCE_OPTIONS } from '../constants/code_mapping';

const queryList = () => {
  return getResourceTree();
}


const buildResourceTreeSelect = (resources) => {
  if (resources.length) {
    return resources.map(resource => {
      const treeData = {
        value: resource.id,
        title: resource.resourceName,
      }
      if (resource.children && resource.children.length) {
        treeData.children = buildResourceTreeSelect(resource.children)
      }
      return treeData;
    })
  }
  return [];
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
  const [addForm] = Form.useForm();
  const [resources, setResources] = useState([])
  const [editingId, setEditingId] = useState('')
  const [openAdd, setOpenAdd] = useState(false);
  const [resourceTreeSelectData, setResourceTreeSelectData] = useState([])
  useEffect(() => {
    const tree = buildResourceTreeSelect(resources);
    setResourceTreeSelectData(tree)
  }, [resources])
  const isEditing = (row) => row.id === editingId
  const cancel = () => {
    setEditingId('');
  };
  const save = async (record) => {
    try {
      const row = await form.validateFields();
      await updateResource({ ...record, ...row })
      setEditingId('')
      query()
    } catch (err) {
      //validate failed
    }

  }

  const query = async () => {
    await queryList().then(res => setResources(res));
  }

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
              onClick={() => save(record)}
              style={{
                marginRight: 8,
              }}
            >
              保存
            </Typography.Link>
            <Popconfirm title="确定取消吗？" onConfirm={cancel} okText="确定" cancelText="取消">
              <Typography.Link>取消</Typography.Link>
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
          rules: col.rules,
          editing: isEditing(record),
        }
      },
    };
  });

  useEffect(() => {
    query()
  }, [])

  const cancelAdd = () => {
    setOpenAdd(false);
    addForm.resetFields()
  }

  const handleAdd = async () => {
    let data;
    try {
      data = await addForm.validateFields();
    }catch(err) {
      return;
    }
    if(data) {
      await addResource(data)
      cancelAdd();
      query();
    }
  }

  return (
    <>
      <Space>
        <Tooltip title="search">
          <Button type="primary" shape="circle" icon={<SearchOutlined />} onClick={() => query()} />
        </Tooltip>
        <Button onClick={() => setOpenAdd(true)}>新增</Button>
        <Modal open={openAdd} onCancel={() => cancelAdd()} onOk={()=> handleAdd()}>
          <Form form={addForm} component={false} labelCol={10} wrapperCol={4} autoComplete={false} >
            <Form.Item label='父级资源' name='parentId' style={{ marginTop: '30px' }} >
              <TreeSelect treeData={resourceTreeSelectData} />
            </Form.Item>
            <Form.Item label='资源编码' name='resourceCode' rules={[
              {
                required: true,
                message: '资源编码必填'
              },
              {
                maxLength: 20
              }
            ]} >
              <Input />
            </Form.Item>
            <Form.Item label='资源名称' name='resourceName' rules={[
              {
                required: true,
                message: '资源名称必填'
              }
            ]} >
              <Input />
            </Form.Item>
            <Form.Item label='资源类型' name='resourceType' rules={[
              {
                required: true,
                message: '资源名称必填'
              }
            ]} >
              <Select options={RESOURCE_OPTIONS} />
            </Form.Item>
            <Form.Item label='资源描述' name='resourceDescription' >
              <Input />
            </Form.Item>
            <Form.Item label='菜单路由' name='resourceUrl' >
              <Input />
            </Form.Item>
            <Form.Item label='资源图标' name='icon' >
              <Input />
            </Form.Item>


          </Form>
        </Modal>
      </Space>
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
    </>
  );
};
export default Menu;