Declare @SQL_SCRIPT varchar(max)
declare @sqlstr varchar(max)
declare @BackupFileName varchar(max)
declare @DBName varchar(max)
DECLARE @file_name_d nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultDataPath'))
DECLARE @file_name_l nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultLogPath'))
set @BackupFileName = '@SQLDB' + '.bak'''
set @DBName = '@SQLDB'  + ''


       
  BEGIN TRY  
                                                                     
                                                                     
  drop DATABASE @SQLDB
                                                         
  END TRY  
  begin catch
     PRINT 'DATABASE NOT EXISTS'                                                                      
  END catch
                                                                     
                                                                  

create database @SQLDB
    
                                                                  
set @sqlstr = 'ALTER DATABASE '+ '@SQLDB'+' '
SET @sqlstr =  @sqlstr +'SET SINGLE_USER WITH '
SET @sqlstr =  @sqlstr +'ROLLBACK IMMEDIATE'
EXEC (@sqlstr);
    
                                                                     declare @sqlstr varchar(max)
SET @SQL_SCRIPT = 'RESTORE DATABASE ' + '@SQLDB' + ' FROM DISK = '''+ @FTPPath + '\' + @BackupFileName + ' WITH REPLACE'
                                                                     
/*use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)*/

EXEC(@sql_script)
go
                                                                     declare @sqlstr varchar(max)
set @sqlstr = 'ALTER DATABASE ' + '@SQLDB'  + ' SET MULTI_USER'
EXEC (@sqlstr);                                                                   
go

GO

*/

