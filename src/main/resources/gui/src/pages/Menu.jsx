import { Space, Switch, Table } from 'antd';
import { useState, useEffect } from 'react';
import { getResourceTree } from '../service/resource';
const columns = [
  {
    title: '资源编码',
    dataIndex: 'resourceCode',
    key: 'resourceCode',
  },
  {
    title: '资源名称',
    dataIndex: 'resourceName',
    key: 'resourceName',
  },
  {
    title: '资源类型',
    dataIndex: 'resourceType',
    key: 'resourceType',
    render: type => {
      if (type === 1) {
        return '功能'
      }
      if (type === 2) {
        return '菜单'
      }
      if (type === 3) {
        return '集合'
      }
    }
  },
  {
    title: '菜单路由',
    dataIndex: 'resourceUrl',
    key: 'resourceUrl',
  },
  {
    title: '菜单图标',
    dataIndex: 'icon',
    key: 'icon',
  },
  {
    title: '菜单排序',
    dataIndex: 'sort',
    key: 'sort',
  },
  {
    title: '创建时间',
    dataIndex: 'createAt',
    key: 'createAt',
  },
];

// rowSelection objects indicates the need for row selection
// const rowSelection = {
//   onChange: (selectedRowKeys, selectedRows) => {
//     console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
//   },
//   onSelect: (record, selected, selectedRows) => {
//     console.log(record, selected, selectedRows);
//   },
//   onSelectAll: (selected, selectedRows, changeRows) => {
//     console.log(selected, selectedRows, changeRows);
//   },
// };
const getData = () => {
  return getResourceTree();
}

const Menu = () => {
  const [resources, setResources] = useState([])
  useEffect(() => {
    const initFetch = async () => {
      setResources(await getData())
    }
    initFetch()
  }, [])

  return (
    <>
      <Table
        columns={columns}
        // rowSelection={{
        //   // ...rowSelection,
        //   checkStrictly: false,
        // }}
        dataSource={resources}
        rowKey="id"
      />
    </>
  );
};
export default Menu;