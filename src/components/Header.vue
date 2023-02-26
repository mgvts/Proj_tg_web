<template>
    <header>
        <div class="languages">
            <a href="#"><img class="flag" src="src/assets/img/gb.png" alt="In English" title="In English"/></a>
<!--            <a href="#"><img class="flag" src="src/assets/img/ru.png" alt="In Russian" title="In Russian"/></a>-->
        </div>
        <div class="enter-or-register-box">
            <template v-if="user">
                {{ user.login }}
                |
                <a href="#" @click.prevent="onLogout">Logout</a>
            </template>
            <template v-else>
                <a href="#" @click.prevent="changePage('Enter')">Enter</a>
                |
                <a href="#" @click.prevent="changePage('Register')">Register</a>
            </template>
        </div>
        <nav>
            <ul>
                <li><a href="#" @click.prevent="changePage('Index')">Home</a></li>
                <li><a href="#"  @click.prevent="changePage('Users')">Users</a></li>
                <li><a href="#"  @click.prevent="changePage('Marks')">Marks</a></li>
                <li><a href="#"  @click.prevent="changePage('User')">FindUser</a></li>
                <li><a href="#"  @click.prevent="changePage('WriteMark')">AddMark</a></li>
            </ul>
        </nav>
    </header>
</template>

<script>
export default {
    name: "Header",
    props: ["user"],
    methods: {
        changePage: function (page) {
            this.$root.$emit("onChangePage", page);
        },
        onLogout: function () {
            this.$root.$emit("onLogout");
            this.changePage("Index");
        }
    }
}
</script>

<style scoped>
  .flag {
    margin-left: 0.3rem;
  }
  .logo {
    max-width: 15rem;
  }
</style>
