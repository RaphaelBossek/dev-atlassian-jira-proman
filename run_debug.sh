#!/bin/sh
set -e
instance="instance-postgres"
#atlas-clean
find target -name "proman-*" -exec rm -rf {} \; || true
rm -rf target/classes
# -Datlassian.webresource.disable.minification=true
atlas-debug --version 6.4.12 --product jira -DskipTests=true -Dmaven.test.skip=true --instanceId $instance $* 2>&1|tee atlas-debug.log
atlas-create-home-zip
d=`date "+%Y%m%d-%H%M%S"`_$instance
b=backup/$d
mkdir -p $b
mv atlas-debug.log $b
mv target/$instance/generated-test-resources.zip $b
cat >$b/dump_postgresql.sh <<__EOF__
#!/bin/sh
/Applications/pgAdmin3.app/Contents/SharedSupport/pg_dump --host macmini-rb --port 5432 --username "jira-amps" --role "jira-amps" --no-password  --format custom --blobs --encoding UTF8 --inserts --column-inserts --verbose --file "dump_postgresql.bin.sql" "jira-amps" 2>&1|tee dump_postgresql.log
__EOF__
(cd $b && sh dump_postgresql.sh)
rm -f backup/last_$instance
ln -s $d backup/last_$instance

