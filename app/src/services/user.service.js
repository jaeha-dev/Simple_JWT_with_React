import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/test/'; // URL 주의

class UserService {
  getPublicContent() {
    return axios.get(API_URL + 'all');
  }

  getUserBoard() {
    // 인증 정보와 함께 요청하기 위해 헬퍼 함수를 사용한다.
    return axios.get(API_URL + "user", { headers: authHeader() });
  }

  getManagerBoard() {
    return axios.get(API_URL + "manager", { headers: authHeader() });
  }

  getAdminBoard() {
    return axios.get(API_URL + "admin", { headers: authHeader() });
  }
}

export default new UserService();