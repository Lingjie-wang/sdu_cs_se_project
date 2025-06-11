// @ts-ignore
/* eslint-disable */
import request from '@/plugins/globalRequest';

/**
 * attendance
 */
/** 获取员工考勤列表 GET /api/attendance/list */
export async function getAttendanceList(params: API.AttendanceListRequest, options?: { [key: string]: any },) {
  return request<API.BaseResponse<API.Attendance>>('/api/attendance/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新员工考勤 POST /api/attendance/update */
export async function updateAttendance(body: API.AttendanceUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.AttendanceUpdateResult>>('/api/attendance/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除员工考勤 POST /api/attendance/delete */
export async function deleteAttendance(body: API.AttendanceDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.AttendanceDeleteResult>>('/api/attendance/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建员工考勤 POST /api/attendance/add */
export async function addAttendance(body: API.AttendanceAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.AttendanceAddResult>>('/api/attendance/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 * salary
 */
/** 获取员工工资列表 GET /api/salary/list */
export async function getSalaryList(params: API.SalaryListRequest, options?: { [key: string]: any },) {
  return request<API.BaseResponse<API.Salary>>('/api/salary/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新员工工资 POST /api/salary/update */
export async function updateSalary(body: API.SalaryUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.SalaryUpdateResult>>('/api/salary/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除员工工资 POST /api/salary/delete */
export async function deleteSalary(body: API.SalaryDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.SalaryDeleteResult>>('/api/salary/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建员工工资 POST /api/salary/add */
export async function addSalary(body: API.SalaryAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.SalaryAddResult>>('/api/salary/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 * training
 */
/** 获取员工培训列表 GET /api/training/list */
export async function getTrainingList(params: API.TrainingListRequest, options?: { [key: string]: any },) {
  return request<API.BaseResponse<API.Training>>('/api/training/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新员工培训 POST /api/training/update */
export async function updateTraining(body: API.TrainingUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.TrainingUpdateResult>>('/api/training/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除员工培训 POST /api/training/delete */
export async function deleteTraining(body: API.TrainingDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.TrainingDeleteResult>>('/api/training/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建员工培训 POST /api/training/add */
export async function addTraining(body: API.TrainingAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.TrainingAddResult>>('/api/training/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 * employee
 */
/** 获取员工列表 GET /api/employee/list */
export async function getEmployeeList(params: API.EmployeeListRequest, options?: { [key: string]: any },) {
  return request<API.BaseResponse<API.Employee>>('/api/employee/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新员工 POST /api/employee/update */
export async function updateEmployee(body: API.EmployeeUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.EmployeeUpdateResult>>('/api/employee/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除员工 POST /api/employee/delete */
export async function deleteEmployee(body: API.EmployeeDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.EmployeeDeleteResult>>('/api/employee/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建员工 POST /api/employee/add */
export async function addEmployee(body: API.EmployeeAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.EmployeeAddResult>>('/api/employee/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 * position
 */
/** 获取级别列表 GET /api/position/list */
export async function getPositionList(params: API.PositionListRequest, options?: { [key: string]: any },) {
  return request<API.BaseResponse<API.Position>>('/api/position/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新级别 POST /api/position/update */
/** 函数名多加 s 区分 umi 内置函数 updatePosition */
export async function updatePositions(body: API.PositionUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.PositionUpdateResult>>('/api/position/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除级别 POST /api/position/delete */
export async function deletePosition(body: API.PositionDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.PositionDeleteResult>>('/api/position/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建级别 POST /api/position/add */
export async function addPosition(body: API.PositionAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.PositionAddResult>>('/api/position/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 * department
 */
/** 获取部门列表 GET /api/department/list */
export async function getDepartmentList(params: API.DepartmentListRequest, options?: { [key: string]: any },) {
  return request<API.BaseResponse<API.Department>>('/api/department/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新部门 POST /api/department/update */
export async function updateDepartment(body: API.DepartmentUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.DepartmentUpdateResult>>('/api/department/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除部门 POST /api/department/delete */
export async function deleteDepartment(body: API.DepartmentDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.DepartmentDeleteResult>>('/api/department/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建部门 POST /api/department/add */
export async function addDepartment(body: API.DepartmentAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.DepartmentAddResult>>('/api/department/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 * user
 */
/** 更新用户 POST /api/department/update */
export async function updateUser(body: API.UserUpdateRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.UserUpdateResult>>('/api/user/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除用户 POST /api/user/delete */
export async function deleteUser(body: API.UserDeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.UserDeleteResult>>('/api/user/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 创建用户 POST /api/user/add */
export async function addUser(body: API.UserAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.UserAddResult>>('/api/user/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取当前的用户 GET /api/user/current */
export async function getCurrentUser(options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentUser>>('/api/user/current', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取用户列表 GET /api/user/list */
export async function getUserList(params: API.UserListRequest, options?: { [key: string]: any },
) {
  return request<API.BaseResponse<API.CurrentUser>>('/api/user/list', {
    method: 'GET',
    // params 请求的查询参数 将用户列表请求的条件传递给后端
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 用户退出登录 POST /api/user/logout */
export async function userLogOut(options?: { [key: string]: any }) {
  return request<API.BaseResponse<number>>('/api/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 用户登录 POST /api/user/login */
export async function userLogIn(body: API.UserLogInRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.UserLogInResult>>('/api/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 用户注册 POST /api/user/register */
export async function userRegister(body: API.UserRegisterRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.UserRegisterResult>>('/api/user/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/**
 *
 */
/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
