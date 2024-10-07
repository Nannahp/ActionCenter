// src/services/EmployeeService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/employee';

const EmployeeService = {
  registerHardcodedEmployee() {
    return axios.post(`${API_URL}/register/hardcoded`);
  },
  getAllEmployees() {
    return axios.get(`${API_URL}`);
  },
};

export default EmployeeService;
