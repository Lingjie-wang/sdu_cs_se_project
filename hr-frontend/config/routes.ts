export default [
  {
    path: '/user', layout: false,
    routes: [
      {name: '登录', path: '/user/login', component: './user/Login'},
      {name: '注册', path: '/user/register', component: './user/Register'},
      {component: './404'},
    ],
  },
  {path: '/welcome', name: '报表中心', icon: 'bar-chart', component: './Welcome'},
  {
    path: '/admin/personnel', access: 'canAdmin', name: '人事管理', icon: 'solution',
    routes: [
      {path: '/admin/personnel/department', name: '部门管理', component: './admin/DeptManage'},
      {path: '/admin/personnel/position', name: '级别管理', component: './admin/PostManage'},
      {path: '/admin/personnel/employee', name: '员工管理', component: './admin/EmpManage'},
      {component: './404'},
    ],
  },
  {path: '/admin/user', access: 'canAdmin', name: '用户管理', icon: 'user', component: './admin/UserManage'},
  {path: '/admin/training', access: 'canAdmin', name: '员工培训', icon: 'schedule', component: './admin/TrainManage'},
  {
    path: '/admin/salary',
    access: 'canAdmin',
    name: '员工工资',
    icon: 'account-book',
    component: './admin/SalaryManage'
  },
  {
    path: '/admin/attendance',
    access: 'canAdmin',
    name: '员工考勤',
    icon: 'crown',
    component: './admin/AttendanceManage'
  },
  {path: '/employee/checkin', name: '考勤打卡', icon: 'contacts', component: './employee/CheckIn'},
  {path: '/', redirect: '/welcome'},
  {component: './404'},
];
