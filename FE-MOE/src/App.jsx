import { Routes, Route, Navigate, Outlet } from "react-router-dom";
import Signin from "~/pages/auth/Signin";
import { Header } from "./components/layout/Header";
import { Sidebar_ } from "./components/layout/Sidebar_";
import { Product } from "./pages/products/Product";
import { Dashboard } from "./pages/other/Dashboard";
import { Customer } from "./pages/customer/Customer";
import { AddCustomer } from "./pages/customer/AddCustomer";
import CustomerDetailPage from './pages/customer/CustomerDetailPage';
import { Categories } from "./pages/products/categories/Categories";
import { Brand } from "./pages/products/brands/Brand";
import { Material } from "./pages/products/materials/Material";
import { Size } from "./pages/products/sizes/Size";
import { Color } from "./pages/products/colors/Color";
import Coupon from "./pages/coupon/Coupon";
import CreateCoupon from "./pages/coupon/create_coupon";
import UpdateCoupon from "./pages/coupon/update_coupon";

const ProtectedRoutes = () => {
  
  const accessToken = localStorage.getItem("accessToken");
  if (!accessToken) {
    return <Navigate to="/login" replace={true} />;
  }
  return (
    <div className="layout">
      <Header />
      <div className="content-area">
        <Sidebar_ />
        <div className="main-content">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

const UnauthorizedRoutes = () => {
  const accessToken = localStorage.getItem("accessToken");
  if (accessToken) {
    return <Navigate to="/dashboard" replace={true} />;
  }
  return (
    <>
      <Outlet />
    </>
  );
};

function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace={true} />} />

      <Route element={<UnauthorizedRoutes />}>
        <Route path="/login" element={<Signin />} />
      </Route>

      <Route element={<ProtectedRoutes />}>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/product" element={<Product />} />
        <Route path="/customer" element={<Customer />} />
        <Route path="/customer/add" element={<AddCustomer />} />
         <Route path="/customer/:id" element={<CustomerDetailPage />} />
        <Route path="/coupon" element={<Coupon />} />
        <Route path="/coupon/create" element={<CreateCoupon />} />
        <Route path="/coupon/detail/:id" element={<UpdateCoupon />} />
        <Route path="/categories" element={<Categories />} />
        <Route path="/brand" element={<Brand />} />
        <Route path="/material" element={<Material />} />
        <Route path="/size" element={<Size />} />
        <Route path="/color" element={<Color />} />
      </Route>
    </Routes>
  );
}

export default App;
