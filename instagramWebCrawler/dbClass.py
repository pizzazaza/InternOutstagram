import pymysql
from datetime import datetime


class DbClass:
    def __init__(self, conInfo):
        self.conn = pymysql.connect(host=conInfo['host'], user=conInfo['user'], password=conInfo['pw'], db=conInfo['db'], charset='utf8')
        self.curs = self.conn.cursor()
        self.insertUserSql = 'INSERT INTO User (user_id, nickname, register_date) VALUES (%s, %s, %s)'
        self.insertPostSql = 'INSERT INTO Post (user_seq, context, create_date) VALUES (%s, %s, %s)'
        self.insertFileSql = \
            'INSERT INTO File (post_seq, path, thumnail_file, original_file, file_length) VALUES (%s, %s, %s, %s, %s)'
        self.selectUserSql = 'SELECT seq FROM User WHERE user_id = %s'

    def __del__(self):
        self.conn.close()

    def insertUser(self, userInfo):
        self.curs.execute(self.insertUserSql, userInfo)
        self.conn.commit()
        return self.curs.lastrowid

    def insertPost(self, postInfo):
        self.curs.execute(self.insertPostSql, postInfo)
        self.conn.commit()
        return self.curs.lastrowid

    def insertFile(self, fileInfo):
        self.curs.execute(self.insertFileSql, fileInfo)
        self.conn.commit()
        return self.curs.lastrowid

    def selectUser(self, userId):
        self.curs.execute(self.selectUserSql, userId)
        seq = int(self.curs.fetchone()[0])
        return seq