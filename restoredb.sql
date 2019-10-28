Declare @SQL_SCRIPT varchar(max)
declare @sqlstr varchar(max)
declare @BackupFileName varchar(max)
declare @SQLDBNAME varchar(max)
DECLARE @file_name_d nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultDataPath'))
DECLARE @file_name_l nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultLogPath'))
set @BackupFileName = '@SQLDB' + '.bak'''
set @SQLDBNAME = '@SQLDB'  + ''

/*IF (EXISTS (SELECT name 
FROM master.dbo.sysdatabases 
WHERE ('[' + name + ']' = N'@SQLDBNAME'
OR name = N'@SQLDBNAME')))
     
drop database N'@SQLDBNAME'

                                                     
CREATE DATABASE  @SQLDBNAME*/
                                                                  
/*

set @sqlstr = 'ALTER DATABASE '+ '@SQLDB'+' '
SET @sqlstr =  @sqlstr +'SET SINGLE_USER WITH '
SET @sqlstr =  @sqlstr +'ROLLBACK IMMEDIATE'
EXEC (@sqlstr);
                                                                     
SET @SQL_SCRIPT = 'RESTORE DATABASE ' + '@SQLDB' + ' FROM DISK = '''+ @FTPPath + '\' + @BackupFileName + ' WITH REPLACE'
                                                                     
use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)

EXEC(@sql_script)

set @sqlstr = 'ALTER DATABASE ' + '@SQLDB'  + ' SET MULTI_USER'
EXEC (@sqlstr);                                                                   


GO

*/

