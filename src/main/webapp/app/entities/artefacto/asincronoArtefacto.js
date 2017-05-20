





function myController (dataService) {  
    dataService
        .getAll()
        .then(function(data) {
            $scope.elementos = data;
        })

}






function dataService ($http, $q) {  
    return {
        getAll: getAll
    }

    function getAll () {
        var defered = $q.defer();
        var promise = defered.promise;

        alert();

        $http.get('http://127.0.0.1:8080/api/artefactos/')
            .success(function(data) {
                defered.resolve(data);
            })
            .error(function(err) {
                defered.reject(err)
            });

        return promise;
    }
}