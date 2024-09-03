db = db.getSiblingDB('admin')
db.createUser( { user: "john", pwd: "johnpassword", roles: [{role: "dbAdminAnyDatabase", db: "admin"}]})
db.grantRolesToUser( "john", [{role: "readWriteAnyDatabase", db: "admin"}])
rs.initiate({'_id':'rs0','members':[{'_id':0,'host':'localhost:27017'}]})
