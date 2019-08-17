Declare @SQL_SCRIPT varchar(max)
declare @sqlstr varchar(max)
declare @BackupFileName varchar(max)
DECLARE @file_name_d nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultDataPath'))
DECLARE @file_name_l nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultLogPath'))
set @BackupFileName = '''@SQLDB' + '.bak'''

                drop database @SQLDB;                                                   
    CREATE DATABASE  @SQLDB;
SET @SQL_SCRIPT = 'RESTORE DATABASE ' + '@SQLDB' + ' FROM DISK = '''+ @FTPPath + '\\' + @BackupFileName + ''' WITH REPLACE'
                                                                     
                                    use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)

 
GO



