package com.zj.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// 授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 请求授权规则
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/level1/**").hasRole("vip1")
				.antMatchers("/level2/**").hasRole("vip2").antMatchers("/level3/**").hasRole("vip3");

		// 默认打开登录页面
		http.formLogin().loginPage("/toLogin").usernameParameter("username").passwordParameter("password")
				.loginProcessingUrl("/login");
		http.rememberMe().rememberMeParameter("remember");
		http.csrf().disable();
		http.logout().logoutSuccessUrl("/");
	}

	// 认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwrodEncoder()).withUser("zhoujian")
				.password(passwrodEncoder().encode("123456")).roles("vip1", "vip2").and().withUser("admin")
				.password(passwrodEncoder().encode("123456")).roles("vip1", "vip2", "vip3").and().withUser("guest")
				.password(passwrodEncoder().encode("123456")).roles("vip1");
	}

	private BCryptPasswordEncoder passwrodEncoder() {
		return new BCryptPasswordEncoder();
	}

}
