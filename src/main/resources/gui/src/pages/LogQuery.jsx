import React from 'react'
import CRUD from '../components/CRUD'
import { SUCCESS_FLAG, SUCCESS_FLAG_OPTIONS , OPERATION_TYPES, OPERATION_TYPES_OPTIONS} from '../constants/code_mapping'
import {Input, Select} from 'antd'
import { getLogPage } from '../service/log'
import { formatDate } from '../utils/date_util'
export default function LogQuery() {

    const columns = [
        {
            title: '操作类型',
            dataIndex: 'operationType',
            render: type => OPERATION_TYPES[type],
            width:100
        },
        {
            title: '操作目标',
            dataIndex: 'operationTarget',
        },
        {
            title: '操作内容',
            dataIndex: 'content',
        },
        {
            title: '操作结果',
            dataIndex: 'successFlag',
            render: flag => SUCCESS_FLAG[flag]
        },
        {
            title: '请求耗时',
            dataIndex: 'costTime',
        },
        {
            title: '失败信息',
            dataIndex: 'exceptionInfo',
            ellipsis:true
        },
        {
            title: '请求参数',
            dataIndex: 'parameter',
            ellipsis:true
        },
        {
            title: '响应结果',
            dataIndex: 'response',
            ellipsis:true
        },
        {
            title: 'IP地址',
            dataIndex: 'ip',
        },
        {
            title: '操作时间',
            dataIndex: 'createAt',
            render: (text) => formatDate(text)
        }
    ]
    const queryConfig = {
        conditions : [
            {
                label : '操作类型',
                name: 'operationType',
                component: <Select options={OPERATION_TYPES_OPTIONS} allowClear />,
                span: 3
            },
            {
                label : '操作目标',
                name: 'operationTarget',
                component: <Input />
            },
            {
                label : '操作结果',
                name: 'successFlag',
                component: <Select options={SUCCESS_FLAG_OPTIONS} allowClear/>
            }
        ],
        doQuery: (conditions) => {
            return getLogPage({...conditions,sort: [{field:'id',direction:'desc'}]})
        }
    }
    const paginationConfig = {

    }
  return (
    <CRUD 
    columns = {columns}
    queryConfig = {queryConfig}
    paginationConfig = {paginationConfig}
    />
  )
}
