package org.slf4j;

import com.badlogic.gdx.Gdx;

public class GdxLogger implements Logger {

	private String name;

	public GdxLogger(String name) {
		this.name = name;
	}

	@Override
	public void debug(String arg0) {
		Gdx.app.debug(name, arg0);
	}

	@Override
	public void debug(String arg0, Object arg1) {
		Gdx.app.debug(name, arg0);
	}

	@Override
	public void debug(String arg0, Object[] arg1) {
		Gdx.app.debug(name, arg0);
	}

	@Override
	public void debug(String arg0, Throwable arg1) {
		Gdx.app.debug(name, arg0, arg1);
	}

	@Override
	public void debug(Marker arg0, String arg1) {
		Gdx.app.debug(name, arg1);
	}

	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
		Gdx.app.debug(name, arg0);
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2) {
		Gdx.app.debug(name, arg1);
	}

	@Override
	public void debug(Marker arg0, String arg1, Object[] arg2) {
		Gdx.app.debug(name, arg1);
	}

	@Override
	public void debug(Marker arg0, String arg1, Throwable arg2) {
		Gdx.app.debug(name, arg1);
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		Gdx.app.debug(name, arg1);
	}

	@Override
	public void error(String arg0) {
		Gdx.app.error(name, arg0);
	}

	@Override
	public void error(String arg0, Object arg1) {
		Gdx.app.error(name, arg0);
	}

	@Override
	public void error(String arg0, Object[] arg1) {
		Gdx.app.error(name, arg0);
	}

	@Override
	public void error(String arg0, Throwable arg1) {
		Gdx.app.error(name, arg0, arg1);
	}

	@Override
	public void error(Marker arg0, String arg1) {
		Gdx.app.error(name, arg1);
	}

	@Override
	public void error(String arg0, Object arg1, Object arg2) {
		Gdx.app.error(name, arg0);
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2) {
		Gdx.app.error(name, arg1);
	}

	@Override
	public void error(Marker arg0, String arg1, Object[] arg2) {
		Gdx.app.error(name, arg1);
	}

	@Override
	public void error(Marker arg0, String arg1, Throwable arg2) {
		Gdx.app.error(name, arg1);
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		Gdx.app.error(name, arg1);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void info(String arg0) {
		Gdx.app.log(name, arg0);
	}

	@Override
	public void info(String arg0, Object arg1) {
		Gdx.app.log(name, arg0);
	}

	@Override
	public void info(String arg0, Object[] arg1) {
		Gdx.app.log(name, arg0);
	}

	@Override
	public void info(String arg0, Throwable arg1) {
		Gdx.app.log(name, arg0);
	}

	@Override
	public void info(Marker arg0, String arg1) {
		Gdx.app.log(name, arg1);
	}

	@Override
	public void info(String arg0, Object arg1, Object arg2) {
		Gdx.app.log(name, arg0);
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2) {
		Gdx.app.log(name, arg1);
	}

	@Override
	public void info(Marker arg0, String arg1, Object[] arg2) {
		Gdx.app.log(name, arg1);
	}

	@Override
	public void info(Marker arg0, String arg1, Throwable arg2) {
		Gdx.app.log(name, arg1);
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		Gdx.app.log(name, arg1);
	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isDebugEnabled(Marker arg0) {
		return true;
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public boolean isErrorEnabled(Marker arg0) {
		return true;
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public boolean isInfoEnabled(Marker arg0) {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return false;
	}

	@Override
	public boolean isTraceEnabled(Marker arg0) {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled(Marker arg0) {
		return false;
	}

	@Override
	public void trace(String arg0) {

	}

	@Override
	public void trace(String arg0, Object arg1) {

	}

	@Override
	public void trace(String arg0, Object[] arg1) {

	}

	@Override
	public void trace(String arg0, Throwable arg1) {

	}

	@Override
	public void trace(Marker arg0, String arg1) {

	}

	@Override
	public void trace(String arg0, Object arg1, Object arg2) {

	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2) {

	}

	@Override
	public void trace(Marker arg0, String arg1, Object[] arg2) {

	}

	@Override
	public void trace(Marker arg0, String arg1, Throwable arg2) {

	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {

	}

	@Override
	public void warn(String arg0) {

	}

	@Override
	public void warn(String arg0, Object arg1) {

	}

	@Override
	public void warn(String arg0, Object[] arg1) {

	}

	@Override
	public void warn(String arg0, Throwable arg1) {

	}

	@Override
	public void warn(Marker arg0, String arg1) {

	}

	@Override
	public void warn(String arg0, Object arg1, Object arg2) {

	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2) {

	}

	@Override
	public void warn(Marker arg0, String arg1, Object[] arg2) {

	}

	@Override
	public void warn(Marker arg0, String arg1, Throwable arg2) {

	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {

	}

}
