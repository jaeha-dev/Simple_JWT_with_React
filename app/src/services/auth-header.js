// 보호된 자원에 접근하는 경우 HTTP 요청에는 반복적으로 인증 헤더를 실어야 하므로 헬퍼 함수를 작성한다.
export default function authHeader() {
  // 로컬 스토리지에서 user 요소를 가져온다.
  const user = JSON.parse(localStorage.getItem("user"));

  // accessToken의 이름은 JwtResponse 클래스(백엔드)의 필드명과 동일하다.
  if (user && user.accessToken) {
    return { Authorization: "Bearer " + user.accessToken }; // for Spring Boot back-end
    // return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
  } else {
    // 없을 경우 빈 객체를 반환한다.
    return {};
  }
}