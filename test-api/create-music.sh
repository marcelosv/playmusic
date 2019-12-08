echo "Executando o curl...";

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{ "name": "Musica 2", "uuidCustomer": "629fa538-6651-49a6-9025-862d6f70f1c8"}' \
  http://localhost:8082/v1/customers/629fa538-6651-49a6-9025-862d6f70f1c8/musics

echo "\n";