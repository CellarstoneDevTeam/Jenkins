Declare @SQL_SCRIPT varchar(max)
declare @sqlstr varchar(max)
declare @BackupFileName varchar(max)
DECLARE @file_name_d nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultDataPath'))
DECLARE @file_name_l nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultLogPath'))

set @BackupFileName =  concat('''',@FTPPath,'\\''',@SQLDB,'''.bak','''')

IF  NOT EXISTS (SELECT * FROM sys.databases WHERE name = '@SQLDB' )
BEGIN
    CREATE DATABASE  @SQLDB
END
RESTORE DATABASE QCBuild FROM DISK = @BackupFileName WITH replace 
                                                                     /*set @sqlstr = 'ALTER DATABASE '+ @SQLDB+' '
SET @sqlstr =  @sqlstr +'SET SINGLE_USER WITH '
SET @sqlstr =  @sqlstr +'ROLLBACK IMMEDIATE'
--EXEC (@sqlstr);
Print (@sqlstr);                                                                     
--$(FTPPath)
----Restore Database
--RESTORE DATABASE QCBuild FROM DISK = @BackupFile WITH replace 
SET @SQL_SCRIPT = 'RESTORE DATABASE ' + @SQLDB + ' FROM DISK = '''+ @FTPPath + '\\' + @BackupFileName + ''' WITH REPLACE'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + @FTPPath + '\Qcommission.bak'' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + ''' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + QUOTENAME(@FTPPath + '\Qcommission.bak') +  'WITH replace;'
--EXECUTE (@SQL_SCRIPT)
print (@SQL_SCRIPT)
/*use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)*/

 
/*If there is no error in statement before database will be in multiuser
mode.
If error occurs please execute following command it will convert
database in multi user.*/
--set @sqlstr = 'ALTER DATABASE ' + @SQLDB  + ' SET MULTI_USER'
--EXEC (@sqlstr);                                                                   
print (@sqlstr); */                                                                   
GO



