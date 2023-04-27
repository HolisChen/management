import {
  Input,
  Select,
  TreeSelect,
  InputNumber
} from 'antd';
import { useState, useEffect } from 'react';
import { getResourceTree, updateResource, addResource, removeResource } from '../service/resource';
import { RESOURCE_TYPES, RESOURCE_OPTIONS } from '../constants/code_mapping';
import CRUD from '../components/CRUD';
import { formatDate } from '../utils/date_util';

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

export default function Menu() {
  const [resources, setResources] = useState([])
  const [resourceTreeSelectData, setResourceTreeSelectData] = useState([])
  useEffect(() => {
      const tree = buildResourceTreeSelect(resources);
      setResourceTreeSelectData(tree)
    }, [resources])
  const columns = [
      {
          title: '资源编码',
          dataIndex: 'resourceCode',
          editable: true,
          component: <Input />,
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
          editable: true,
          component: <Input />,
      },
      {
          title: '资源类型',
          dataIndex: 'resourceType',
          editable: true,
          render: type => RESOURCE_TYPES[type],
          component: <Select options={RESOURCE_OPTIONS} />,

      },
      {
          title: '菜单路由',
          dataIndex: 'resourceUrl',
          editable: true,
          component: <Input />,
      },
      {
          title: '菜单图标',
          dataIndex: 'icon',
          editable: true,
          component: <Input />,
      },
      {
          title: '菜单排序',
          dataIndex: 'sort',
          editable: true,
          component: <InputNumber />,
      },
      {
          title: '创建时间',
          dataIndex: 'createAt',
          render: (text) => formatDate(text)
      }]

  const queryConfig = {
      doQuery: (conditions) => {
          return getResourceTree()
      },
      onDatasourceChange:(datasource) => {
          setResources(datasource)
      }
  }
  const updateConfig = {
      doUpdate: (row, data) => {
          return updateResource({ ...data, id: row.id })
      }
  }
  const deleteConfig = {
      mode: 'batch',
      buttonName: '删除所选',
      doDelete: (rowIds) => {
          return removeResource(rowIds)
      }
  }

  const createConfig = {
      modalTitle: '新建资源',
      createFormItems: [
          {
              name: 'parentId',
              label: '父级资源',
              rules: [

              ],
              component: <TreeSelect treeData={resourceTreeSelectData}/>
          },
          {
              name: 'resourceCode',
              label: '资源编码',
              rules: [
                  {
                      'required': true,
                      'message': '资源编码必填'
                  }
              ],
              component: <Input />
          },
          {
              name: 'resourceName',
              label: '资源名称',
              rules: [
                  {
                      required: true,
                      message: '资源名称必填'
                  }
              ],
              component: <Input />
          },
          {
              name: 'resourceType',
              label: '资源类型',
              rules: [
                  {
                      required: true,
                      message: '资源类型必填'
                    }
              ],
              component:  <Select options={RESOURCE_OPTIONS} />
          },
          {
              name: 'resourceDescription',
              label: '资源描述',
              rules: [
                  
              ],
              component: <Input />
          },
          {
              name: 'resourceUrl',
              label: '菜单路由',
              rules: [
              ],
              component: <Input />
          },
          {
              name: 'icon',
              label: '资源图标',
              rules: [
              ],
              component: <Input />
          }
      ],
      doCreate: (data) => {
          return addResource(data)
      }
  }
  return (
      <CRUD
          columns={columns}
          queryConfig={queryConfig}
          updateConfig={updateConfig}
          deleteConfig={deleteConfig}
          createConfig={createConfig}
      />
  )
}
