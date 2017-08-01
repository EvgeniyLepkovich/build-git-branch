mvn -f $1 clean install
status=$?
result=""

if [ $status -eq 0 ]; then
	result="Build Successful"
else
	result="Build Failed"
fi
echo $result
echo status: $status


