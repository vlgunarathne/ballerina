package req2;

public struct userPB {
    int age;
    string name;
    string address;
}

public function <userPB ub> getName () returns (string) {
    return ub.name;
}

public function <userPB ub> getAge () returns (int) {
    return ub.age;
}