#/bin/sh
function addslots()
{
for i in `seq  $2 $3`;do
 redis-cli  -p $1 cluster addslots $i  
done
}
case $1 in
    addslots)
    echo "add slots ..."
    addslots $2 $3 $4;
  ;;
  *)
    echo "Usage: inetpanel [addslots]"
  ;;
esac
