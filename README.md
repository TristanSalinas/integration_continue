### Configurer le projet

- lancer les trois docker dans cicd/jenkins avec docker-compose
- aller sur localhost:8080
- http://localhost:8080/manage/configureTools/
  - Maven installation (set la 3.9)

- créer un node avec
  - name: "linux-agent"
  - remote root directory : "/home/jenkins/agent"
  - save
  - recupérer son secret : le mettre dans /agent/linux/start-agent.sh

- installer le pluggin git

- créer un Item Mvn : le nommer comme on veut
  - restrict where this project can be run : linux-agent (nom de l'agent)
  - mettre le git avec les credential (ne pas oublier de mettre les autorisations depuis l'interface web de github)
  - mettre la branche (main)
  - On passe les variables d'environnemenet un peut bourin comme ca, il existe un pluggin pour le faire mais ca ne marchait pas chez moi. Dans `Goals and options` : clean test -Dmysql.host=mysql-db -Dmysql.port=3306 -Dmysql.database=bibliotheque -Dmysql.user=root -Dmysql.password=password
  - save

- on rebuild l'agent avec le bon secret
  - docker-compose stop jenkins-agent-linux
  - docker compose build jenkins-agent-linux
  - docker-compose up -d jenkins-agent-linux

- normalement l'agent est online maintenant sur localhost:8080, on peut vérifier avec les logs : `docker logs -f jenkins-agent-linux`
  on doit y voir `Connected`

- on peut lancer un build normalement ca fonctionne 
