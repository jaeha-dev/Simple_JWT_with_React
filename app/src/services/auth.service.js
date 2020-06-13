import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/"; // URL 주의

class AuthService {
  // 로그인: POST 요청 및 로컬 스토리지에 JWT 저장
  login(username, password) {
    return axios
      .post(API_URL + "login", {
        username,
        password
      })
      .then(response => {
        if (response.data.accessToken) {
          // JSON.stringify(): JS 값이나 객체를 JSON 데이터로 변환한다.
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  // 로그아웃: user 정보를 삭제한다.
  logout() {
    localStorage.removeItem("user");
  }

  // 계정 등록: POST 요청
  join(username, email, password) {
    return axios.post(API_URL + "join", {
      username,
      email,
      password
    });
  }

  // 현재 로그인 정보: 로컬 스토리지에서 JWT 추출
  getCurrentUser() {
    // JSON.parse(): JSON 데이터를 객체나 JS 값으로 변환한다.
    return JSON.parse(localStorage.getItem("user"));;
  }
}

export default new AuthService();