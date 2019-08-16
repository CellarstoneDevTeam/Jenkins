Declare @SQL_SCRIPT varchar(max)
IF  NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'QCommission')
BEGIN
    
   CREATE DATABASE QCommission
   ON
   ( NAME = QCommissionMDF,  
       FILENAME = 'D:\QCommissionMDFFile.mdf',
       SIZE = 10,
       MAXSIZE = 50,
       FILEGROWTH = 5 )  
   LOG ON
   ( NAME = QCommissionLDF,  
       FILENAME = 'D:\QCommissionLDFFile.ldf',
       SIZE = 5,
       MAXSIZE = 25,
       FILEGROWTH = 5 )
   
END
ALTER DATABASE QCommission
SET SINGLE_USER WITH
ROLLBACK IMMEDIATE
--$(FTPPath)
----Restore Database
--RESTORE DATABASE QCBuild FROM DISK = @BackupFile WITH replace 
SET @SQL_SCRIPT = 'RESTORE DATABASE QCommission FROM DISK = '''+ @FTPPath + '\Qcommission.bak''' + ' WITH REPLACE'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + @FTPPath + '\Qcommission.bak'' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + ''' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + QUOTENAME(@FTPPath + '\Qcommission.bak') +  'WITH replace;'
EXECUTE (@SQL_SCRIPT)
/*use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)*/

 
/*If there is no error in statement before database will be in multiuser
mode.
If error occurs please execute following command it will convert
database in multi user.*/
ALTER DATABASE QCommission SET MULTI_USER
GO

