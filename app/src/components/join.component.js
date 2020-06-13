import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

import AuthService from "../services/auth.service";

// 필수 입력 항목
const required = value => {
  if (! value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

// 계정 이메일 유효성 검사
const inEmail = value => {
  if (! isEmail(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        This is not a valid email.
      </div>
    );
  }
};

// 계정 ID 유효성 검사
const inUsername = value => {
  if (value.length < 5 || value.length > 20) {
    return (
      <div className="alert alert-danger" role="alert">
        The username must be between 5 and 20 characters.
      </div>
    );
  }
};

// 계정 비밀번호 유효성 검사
const inPpassword = value => {
  if (value.length < 5 || value.length > 40) {
    return (
      <div className="alert alert-danger" role="alert">
        The password must be between 5 and 40 characters.
      </div>
    );
  }
};

export default class Join extends Component {
  constructor(props) {
    super(props);
    // 이벤트 바인딩
    this.handleJoin = this.handleJoin.bind(this);
    this.onChangeUsername = this.onChangeUsername.bind(this);
    this.onChangeEmail = this.onChangeEmail.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);

    // 편의상 값 지정
    this.state = {
      username: "user1",
      email: "user1@test.com",
      password: "user1",
      successful: false,
      message: ""
    };
  }

  // e.target.value: 이벤트 객체에 담겨있는 현재 텍스트의 값을 가져온다.
  onChangeUsername(e) {
    this.setState({
      username: e.target.value
    });
  }

  onChangeEmail(e) {
    this.setState({
      email: e.target.value
    });
  }

  onChangePassword(e) {
    this.setState({
      password: e.target.value
    });
  }

  handleJoin(e) {
    // e.preventDefault()는 a 태그나 submit 태그의 고유 동작을 중단시킨다.
    // e.stopPropagation()는 상위 요소로의 이벤트 전파를 중단시킨다.
    e.preventDefault();

    this.setState({
      message: "",
      successful: false
    });

    // 유효성 검사 기능 확인
    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      AuthService.join(
        this.state.username,
        this.state.email,
        this.state.password
      ).then(
        response => {
          this.setState({
            message: response.data.message,
            successful: true
          });
        },
        error => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            successful: false,
            message: resMessage
          });
        }
      );
    }
  }

  render() {
    return (
      <div className="col-md-12">
        <div className="card card-container">
          <img
            src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
            alt="profile-img"
            className="profile-img-card"
          />

          {/* ref: 없을 경우 validateAll() 에러 발생 */}
          <Form onSubmit={this.handleJoin} ref={c => {this.form = c;}}>
            {! this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="username">Username</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChangeUsername}
                    validations={[required, inUsername]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="email"
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    validations={[required, inEmail]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChangePassword}
                    validations={[required, inPpassword]}
                  />
                </div>

                <div className="form-group">
                  <button className="btn btn-primary btn-block">Join</button>
                </div>
              </div>
            )}

            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            
            {/* 유효성 검사 결과 */}
            <CheckButton style={{ display: "none" }} ref={c => {this.checkBtn = c;}}/>
          </Form>
        </div>
      </div>
    );
  }
}
