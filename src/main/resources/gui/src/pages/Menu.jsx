import {
  Form,
  Popconfirm,
  Table,
  Typography,
  Input,
  Select,
  Button,
  Space,
  Tooltip,
  Modal,
  TreeSelect,
  InputNumber
} from 'antd';
import { useState, useEffect } from 'react';
import {getResourceTree, updateResource, addResource, removeResource} from '../service/resource';
import { RESOURCE_TYPES, RESOURCE_OPTIONS } from '../constants/code_mapping';
import EditableCell from '../components/EditableCell';

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

const Menu = () => {
  const [form] = Form.useForm();
  const [addForm] = Form.useForm();
  const [resources, setResources] = useState([])
  const [editingId, setEditingId] = useState('')
  const [openAdd, setOpenAdd] = useState(false);
  const [deleteKeys, setDeleteKeys] = useState([]);
  const [resourceTreeSelectData, setResourceTreeSelectData] = useState([])
  useEffect(() => {
    const tree = buildResourceTreeSelect(resources);
    setResourceTreeSelectData(tree)
  }, [resources])

  const isEditing = (row) => row.id === editingId

  const cancel = () => {
    setEditingId('');
  };
  const saveEdit = async (record) => {
    try {
      const row = await form.validateFields();
      await updateResource({ ...record, ...row })
      setEditingId('')
      await query()
    } catch (err) {
      //validate failed
    }
  }

  const query = async () => {
    await getResourceTree()
      .then(res => {
        setResources(res);
      });
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
      editable: true,
      onCell: (record) => ({
        record,
        name: 'resourceCode',
        editing: isEditing(record),
        component: <Input />,
        rules: [
          {
            required: true,
            message: '资源编码必填',
          },
        ]
    })
    },
    {
      title: '资源名称',
      dataIndex: 'resourceName',
      editable: true,
      onCell: (record) => ({
        record,
        name: 'resourceName',
        editing: isEditing(record),
        component: <Input />,
      })  
    },
    {
      title: '资源类型',
      dataIndex: 'resourceType',
      editable: true,
      render: type => RESOURCE_TYPES[type],
      onCell: (record) => ({
        record,
        name: 'resourceType',
        editing: isEditing(record),
        component: <Select options={RESOURCE_OPTIONS} />,
      })
    },
    {
      title: '菜单路由',
      dataIndex: 'resourceUrl',
      editable: true,
      onCell: (record) => ({
        record,
        name: 'resourceUrl',
        editing: isEditing(record),
        component: <Input />,
      })
    },
    {
      title: '菜单图标',
      dataIndex: 'icon',
      editable: true,
      onCell: (record) => ({
        record,
        name: 'icon',
        editing: isEditing(record),
        component: <Input />,
      })
    },
    {
      title: '菜单排序',
      dataIndex: 'sort',
      editable: true,
      onCell: (record) => ({
        record,
        name: 'sort',
        editing: isEditing(record),
        component: <InputNumber />,
      })
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
              onClick={() => saveEdit(record)}
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
    } catch (err) {
      return;
    }
    if (data) {
      await addResource(data)
      cancelAdd();
      await query();
    }
  }

  /**
   * 处理资源选中改变
   * @param selectedRowKeys
   * @param selectedRows
   */
  const handleSelectChange = (selectedRowKeys, selectedRows) => {
    setDeleteKeys(selectedRowKeys)
  }

  /**
   * 删除数据
   */
  const handleDelete =  () => {
     removeResource(deleteKeys).then(res => {
       setDeleteKeys([])
       query()
    })
  }
  return (
    <>
      <Space style={{marginLeft:'15px'}}>
        <Tooltip title="search">
          <Button type="primary"  onClick={() => query()}>查询</Button>
        </Tooltip>
        <Button onClick={() => setOpenAdd(true)}>新增</Button>
        <Popconfirm title={'删除资源'} description={'确定要删除所选资源吗？'} okText={'是的'} cancelText={'点错了'} onConfirm={() => {handleDelete()}} disabled={!deleteKeys.length}>
          <Button disabled={!deleteKeys.length} type={'primary'} danger>删除所选</Button>
        </Popconfirm>
        <Modal open={openAdd} onCancel={() => cancelAdd()} onOk={() => handleAdd()}>
          <Form form={addForm} component={false} labelCol={10} wrapperCol={4} autoComplete={false}>
            <Form.Item label='父级资源' name='parentId' style={{ marginTop: '30px' }}>
              <TreeSelect treeData={resourceTreeSelectData} />
            </Form.Item>
            <Form.Item label='资源编码' name='resourceCode' rules={[
              {
                required: true,
                message: '资源编码必填'
              }
            ]}>
              <Input />
            </Form.Item>
            <Form.Item label='资源名称' name='resourceName' rules={[
              {
                required: true,
                message: '资源名称必填'
              }
            ]}>
              <Input />
            </Form.Item>
            <Form.Item label='资源类型' name='resourceType' rules={[
              {
                required: true,
                message: '资源名称必填'
              }
            ]}>
              <Select options={RESOURCE_OPTIONS} />
            </Form.Item>
            <Form.Item label='资源描述' name='resourceDescription'>
              <Input />
            </Form.Item>
            <Form.Item label='菜单路由' name='resourceUrl'>
              <Input />
            </Form.Item>
            <Form.Item label='资源图标' name='icon'>
              <Input />
            </Form.Item>
          </Form>
        </Modal>
      </Space>
      <Form form={form} component={false}>
        <Table
          columns={columns}
          bordered
          components={{
            body: {
              cell: EditableCell,
            },
          }}
          rowSelection={{
            // ...rowSelection,
            checkStrictly: true,
            onChange: handleSelectChange
          }}
          dataSource={resources}
          rowKey="id"
        />
      </Form>
    </>
  );
};
export default Menu;