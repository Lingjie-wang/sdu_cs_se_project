// @ts-ignore
/* eslint-disable */

declare namespace API {
  /**
   * attendance
   */
  type Attendance = {
    id?: number;
    empId?: number;
    empName?: string;
    attendanceDate?: Date;
    attendanceType?: number;
  };

  type AttendanceListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    empId?: number;
    empName?: string;
    attendanceDate?: Date;
    attendanceType?: number;
  };

  type AttendanceAddRequest = {
    empId?: number;
    empName?: string;
    attendanceDate?: Date;
    attendanceType?: number;
  };

  type AttendanceAddResult = number;

  type AttendanceDeleteRequest = {
    id?: number;
  };

  type AttendanceDeleteResult = boolean;

  type AttendanceUpdateRequest = {
    id?: number;
    empId?: number;
    empName?: string;
    attendanceDate?: Date;
    attendanceType?: number;
  };

  type AttendanceUpdateResult = boolean;

  /**
   * salary
   */
  type Salary = {
    id?: number;
    empId?: number;
    empName?: string;
    postId?: number;
    postTitle?: string;
    wage?: number;
    bonus?: number;
    createTime?: Date;
  };

  type SalaryListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    empId?: number;
    empName?: string;
    postId?: number;
    postTitle?: string;
    wage?: number;
    bonus?: number;
    createTime?: Date;
  };

  type SalaryAddRequest = {
    empId?: number;
    empName?: string;
    postId?: number;
    postTitle?: string;
    wage?: number;
    bonus?: number;
  };

  type SalaryAddResult = number;

  type SalaryDeleteRequest = {
    id?: number;
  };

  type SalaryDeleteResult = boolean;

  type SalaryUpdateRequest = {
    id?: number;
    empId?: number;
    empName?: string;
    postId?: number;
    postTitle?: string;
    wage?: number;
    bonus?: number;
    createTime?: Date;
  };

  type SalaryUpdateResult = boolean;

  /**
   * training
   */
  type Training = {
    id?: number;
    empId?: number;
    empName?: string;
    trainingProject?: number;
    trainingDate?: Date;
    trainingDuration?: number;
    trainer?: string;
  };

  type TrainingListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    empId?: number;
    empName?: string;
    trainingProject?: number;
    trainingDate?: Date;
    trainingDuration?: number;
    trainer?: string;
  };

  type TrainingAddRequest = {
    empId?: number;
    empName?: string;
    trainingProject?: number;
    trainingDate?: Date;
    trainingDuration?: number;
    trainer?: string;
  };

  type TrainingAddResult = number;

  type TrainingDeleteRequest = {
    id?: number;
  };

  type TrainingDeleteResult = boolean;

  type TrainingUpdateRequest = {
    id?: number;
    empId?: number;
    empName?: string;
    trainingProject?: number;
    trainingDate?: Date;
    trainingDuration?: number;
    trainer?: string;
  };

  type TrainingUpdateResult = boolean;

  /**
   * employee
   */
  type Employee = {
    id?: number;
    empName?: string;
    empGender?: number;
    empEmail?: string;
    empPhone?: string;
    postId?: number;
    postTitle?: string;
    deptId?: number;
    deptName?: string;
    hireDate?: Date;
    resignDate?: Date;
    empStatus?: number;
  };

  type EmployeeListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    empName?: string;
    empGender?: number;
    empEmail?: string;
    empPhone?: string;
    postId?: number;
    postTitle?: string;
    deptId?: number;
    deptName?: string;
    hireDate?: Date;
    resignDate?: Date;
    empStatus?: number;
  };

  type EmployeeAddRequest = {
    empName?: string;
    empGender?: number;
    empEmail?: string;
    empPhone?: string;
    postId?: number;
    postTitle?: string;
    deptId?: number;
    deptName?: string;
    hireDate?: Date;
    resignDate?: Date;
    empStatus?: number;
  };

  type EmployeeAddResult = number;

  type EmployeeDeleteRequest = {
    id?: number;
  };

  type EmployeeDeleteResult = boolean;

  type EmployeeUpdateRequest = {
    id?: number;
    empName?: string;
    empGender?: number;
    empEmail?: string;
    empPhone?: string;
    postId?: number;
    postTitle?: string;
    deptId?: number;
    deptName?: string;
    hireDate?: Date;
    resignDate?: Date;
    empStatus?: number;
  };

  type EmployeeUpdateResult = boolean;

  /**
   * position
   */
  type Position = {
    id?: number;
    title?: string;
    duty?: string;
  };

  type PositionListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    title?: string;
    duty?: string;
  };

  type PositionAddRequest = {
    title?: string;
    duty?: string;
  };

  type PositionAddResult = number;

  type PositionDeleteRequest = {
    id?: number;
  };

  type PositionDeleteResult = boolean;

  type PositionUpdateRequest = {
    id?: number;
    title?: string;
    duty?: string;
  };

  type PositionUpdateResult = boolean;

  /**
   * department
   */
  type Department = {
    id?: number;
    name?: string;
    responsibility?: string;
  };

  type DepartmentListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    name?: string;
    responsibility?: string;
  };

  type DepartmentAddRequest = {
    name?: string;
    responsibility?: string;
  };

  type DepartmentAddResult = number;

  type DepartmentDeleteRequest = {
    id?: number;
  };

  type DepartmentDeleteResult = boolean;

  type DepartmentUpdateRequest = {
    id?: number;
    name?: string;
    responsibility?: string;
  };

  type DepartmentUpdateResult = boolean;

  /**
   * user
   */
  type CurrentUser = {
    id?: number;
    userName?: string;
    userAccount?: string;
    userAvatar?: string;
    userRole?: number;
    registerTime: Date;
  };

  type UserLogInRequest = {
    userAccount?: string;
    userPassword?: string;
    autoLogin?: boolean;
    type?: string;
  };

  type UserLogInResult = {
    status?: string;
    type?: string;
    currentAuthority?: string;
  };

  type UserRegisterRequest = {
    userAccount?: string;
    userPassword?: string;
    checkPassword?: string;
    type?: string;
  };

  type UserRegisterResult = number;

  type UserListRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: number;
    userName?: string;
    userAccount?: string;
    userAvatar?: string;
    userRole?: string;
    registerTime?: Date;
  };

  type UserAddRequest = {
    userName?: string;
    userAccount?: string;
    userPassword?: string;
    userAvatar?: string;
    userRole?: number;
  };

  type UserAddResult = number;

  type UserDeleteRequest = {
    id?: number;
  };

  type UserDeleteResult = boolean;

  type UserUpdateRequest = {
    id?: number;
    userName?: string;
    userAccount?: string;
    userPassword?: string;
    userAvatar?: string;
    userRole?: number;
  };

  type UserUpdateResult = boolean;

  /**
   * 通用返回类
   */
  type BaseResponse<T> = {
    code: number,
    data: T,
    message: string,
  }

  /**
   *
   */
  type PageParams = {
    current?: number;
    pageSize?: number;
  };

  type RuleListItem = {
    key?: number;
    disabled?: boolean;
    href?: string;
    avatar?: string;
    name?: string;
    owner?: string;
    desc?: string;
    callNo?: number;
    status?: number;
    updatedAt?: string;
    createdAt?: string;
    progress?: number;
  };

  type RuleList = {
    data?: RuleListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type FakeCaptcha = {
    code?: number;
    status?: string;
  };

  type ErrorResponse = {
    /** 业务约定的错误码 */
    errorCode: string;
    /** 业务上的错误信息 */
    errorMessage?: string;
    /** 业务上的请求是否成功 */
    success?: boolean;
  };

  type NoticeIconList = {
    data?: NoticeIconItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type NoticeIconItemType = 'notification' | 'message' | 'event';

  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
}
