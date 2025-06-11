import {DefaultFooter} from '@ant-design/pro-components';

const Footer: React.FC = () => {
  const defaultMessage = 'By Jzq';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'HR Pro',
          title: 'HR Pro',
          href: 'https://pro.ant.design',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
