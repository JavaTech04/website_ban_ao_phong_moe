import { Sidebar, Menu, MenuItem, SubMenu } from "react-pro-sidebar";
import { useNavigate } from "react-router-dom";
import { handleLogoutAPI } from "~/apis";
import { Link } from 'react-router-dom';

export const Sidebar_ = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await handleLogoutAPI();
    navigate("/");
    setUser(null);
  };
  // Icon link https://fontawesome.com/search
  return (
    <Sidebar backgroundColor="fff">
      <Menu>
        <MenuItem icon={<i className="fa-solid fa-house"></i>} component={<Link to="/"/>}> Trang chủ </MenuItem>
        <SubMenu label="Bán hàng" icon={<i className="fa-solid fa-cart-shopping"></i>}>
          <MenuItem component={<Link to="/dashboard?offline"/>}> Offline </MenuItem>
          <MenuItem> Online </MenuItem>
        </SubMenu>
        <SubMenu label="Sản phẩm" icon={<i className="fa-solid fa-shirt"></i>}>
          <MenuItem component={<Link to="/product"/>}> Quản lý sản phẩm </MenuItem>
          <MenuItem component={<Link to="categories"/>}> Quản lý danh mục </MenuItem>
          <MenuItem component={<Link to="/brand"/>}> Quản lý thương hiệu </MenuItem>
          <MenuItem component={<Link to="/material"/>}> Quản lý chất liệu </MenuItem>
          <SubMenu label="Thuộc tính sản phẩm">
            <MenuItem component={<Link to="/size"/>}> Quản lý size </MenuItem>
            <MenuItem component={<Link to="/color"/>}> Quản lý color </MenuItem>
          </SubMenu>
        </SubMenu>
        <SubMenu label="Cusomer" icon={<i className="fa-solid fa-users"></i>}>
          <MenuItem> ABC </MenuItem>
          <MenuItem> ABC </MenuItem>
        </SubMenu>
        <SubMenu label="Other" icon={<i className="fa-solid fa-link"></i>}>
          <MenuItem> ABC </MenuItem>
          <MenuItem> ABC </MenuItem>
        </SubMenu>
      </Menu>
      <Menu>
        <MenuItem icon={<i className="fa-solid fa-user-circle"></i>}> Profile </MenuItem>
        <MenuItem icon={<i className="fa-solid fa-sign-out-alt"></i>} onClick={handleLogout}> Logout </MenuItem>
      </Menu>
    </Sidebar>
  );
};