import {Settings as LayoutSettings} from '@ant-design/pro-components';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'dark',
  primaryColor: '#13C2C2',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: 'HR Pro',
  pwa: false,
  logo: 'https://cdn.worldvectorlogo.com/logos/human-resources.svg',
  iconfontUrl: '',
};

export default Settings;
