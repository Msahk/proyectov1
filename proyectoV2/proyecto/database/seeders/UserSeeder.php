<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('users')->insert([
           'name'=>'Marlon Avila',
           'cod_tipo_fk'=> 'A',
           'email'=> 'marlon@correo.com',
           'password'=> Hash::make('1234'),
        ]);
    }
}
