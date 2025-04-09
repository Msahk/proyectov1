@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
            @if (Auth::user()->cod_tipo_fk== 'A')
                <div class="card-header">{{ __('Bienvenido Administrador') }}</div>
                @else
                <div class="card-header">{{ __('Bienvenido Empleado') }}</div>

            @endif
                <div class="card-body">
                    @if (session('status'))
                        <div class="alert alert-success" role="alert">
                            {{ session('status') }}
                        </div>
                    @endif
                    @if (Auth::user()->cod_tipo_fk== 'A')
                    {{ __('Opciones de Administracion') }}
                    @else
                    {{ __('Opciones de Empleado') }}
                    @endif
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
