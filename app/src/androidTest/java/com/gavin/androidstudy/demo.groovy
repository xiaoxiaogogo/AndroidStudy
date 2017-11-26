
/*
    声明变量: def
     1.声明变量的类型可以省略
     2.方法的返回值可以省略
     3.方法调用的时候 括号可以省略

 */

def a = 1
def b = 'haha'
def String c = "a=${a}, b=${b}" // 这是在字符串拼接外部的变量
def sayHi(String c){
    println c
    return 12
}
sayHi(a + b + c)


/*
    闭包：

 */

def closure1 = {String str ->
    println(str)
}

// 这里没有类型的参数不能是上面已经定义的变量
def closure2 = { p1, p2 ->
    // 注意： 闭包可以访问外部的变量； 但是 普通的方法是无法访问外部的变量的
    a + b // 默认， return
}
closure1 'fuck'
println closure2(5, 6)

def closure3 = {
    println('默认参数it的值：${it}.....')
}
closure3('default val')


