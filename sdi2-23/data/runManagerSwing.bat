cd ..\data
@java -classpath ..\lib\hsqldb.jar org.hsqldb.util.DatabaseManagerSwing -url jdbc:hsqldb:hsql://localhost/task_manager_db -driver org.hsqldb.jdbcDriver